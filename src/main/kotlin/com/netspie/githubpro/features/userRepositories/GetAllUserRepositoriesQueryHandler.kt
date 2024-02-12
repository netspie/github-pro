package com.netspie.githubpro.features.userRepositories

import com.fasterxml.jackson.annotation.JsonProperty
import com.netspie.githubpro.features.shared.HttpClient
import com.netspie.githubpro.features.shared.ResultT
import com.netspie.githubpro.features.shared.resultActionOfT
import org.springframework.stereotype.Service

const val GithubUrl = "https://api.github.com"

@Service
class GetAllUserRepositoriesQueryHandler{
    fun execute(query: GetAllUserRepositoriesQuery): ResultT<GetAllUserRepositoriesQueryResponse> =
        resultActionOfT { result ->
            if (query.username.isEmpty())
                result.fail("Username cannot be empty") && return@resultActionOfT null

            val client = HttpClient(query.authorizationToken)
            val urls = client.get<GithubUrlsDTO>(GithubUrl) ?: return@resultActionOfT null

            val url = urls.userRepositoriesUrl
                .replace("{user}", query.username)
                .removeSuffix("{?type,page,per_page,sort}")

            val repositories = runCatching {
                client.get<Array<GithubRepositoryDTO>>(url)
            }.getOrElse {
                result.fail(it.message ?: "Error occurred")
                null
            }

            if (repositories == null || !result.isSuccess)
                return@resultActionOfT null

            GetAllUserRepositoriesQueryResponse(
                repositories
                    .filter { !it.fork }
                    .map { repo ->
                        GetAllUserRepositoriesQueryResponse.RepositoryDTO(
                            repo.fullName,
                            repo.owner.login,
                            branches = getBranches(client, repo.branchesUrl).let {
                                it ?: return@resultActionOfT null
                            }
                        )
                    }
            )
        }

    private fun getBranches(client: HttpClient, branchesUrl: String): List<GetAllUserRepositoriesQueryResponse.BranchDTO>? {
        val branches = runCatching {
            client.get<Array<GithubBranchDTO>>(branchesUrl.removeSuffix("{/branch}"))
        }.getOrElse {
            null
        } ?: return null

        return branches.map {
            branch ->
            GetAllUserRepositoriesQueryResponse.BranchDTO(
                branch.name,
                branch.commit.sha
            )
        }
    }

    data class GithubUrlsDTO(
        @JsonProperty("user_repositories_url")
        val userRepositoriesUrl: String
    )

    data class GithubRepositoryDTO(
        @JsonProperty("full_name")
        val fullName: String,

        @JsonProperty("branches_url")
        val branchesUrl: String,

        val fork: Boolean,
        val owner: GithubRepositoryOwnerDTO,
    )

    data class GithubRepositoryOwnerDTO(
        val login: String
    )

    data class GithubBranchDTO(
        val name: String,
        val commit: GithubCommitDTO
    )

    data class GithubCommitDTO(
        val sha: String
    )
}

