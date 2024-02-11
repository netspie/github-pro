package com.netspie.githubpro.features.repositories

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GetAllUserRepositoriesQueryHandler(
    private val restTemplate: RestTemplate
) {

    fun execute(query: GetAllUserRepositoriesQuery): GetAllUserRepositoriesQuery.Response {
        val repositories = restTemplate.getForObject(
            "https://api.github.com/users/netspie/repos?type=all&sort=updated&direction=desc",
            Array<Foo>::class.java)

        return GetAllUserRepositoriesQuery.Response(
            repositories!!.map {
                GetAllUserRepositoriesQuery.Response.RepositoryDTO(
                    it.full_name,
                    "",
                    emptyList()
                )
            }
        )
    }
}

data class Foo(
    val id: Int,
    val node_id: String,
    val full_name: String
)

data class GetAllUserRepositoriesQuery(
    val username: String) {

    data class Response(
        val repositories: List<RepositoryDTO>) {

        data class RepositoryDTO(
            val name: String,
            val username: String,
            val branches: List<BranchDTO>
        ) {

            data class BranchDTO(
                val name: String,
                val lastCommitSha: String
            )
        }
    }
}

