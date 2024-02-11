package com.netspie.githubpro.repositories

class GetAllUserRepositoriesQueryHandler {

    fun execute(query: GetAllUserRepositoriesQuery): GetAllUserRepositoriesQuery.Response =
        GetAllUserRepositoriesQuery.Response(
            listOf(
                GetAllUserRepositoriesQuery.Response.RepositoryDTO(
                    "github-pro",
                    "netspie",
                    branches = listOf(
                        GetAllUserRepositoriesQuery.Response.RepositoryDTO.BranchDTO(
                            "master",
                            "a7f76d9c6a8"
                        )
                    )
                )
            )
        )
}

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

