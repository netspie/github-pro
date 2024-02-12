package com.netspie.githubpro.features.userRepositories

data class GetAllUserRepositoriesQuery(
    val username: String,
    val authorizationToken: String? = null) {
}

data class GetAllUserRepositoriesQueryResponse(
    val repositories: List<RepositoryDTO>) {

    data class RepositoryDTO(
        val name: String,
        val login: String,
        val branches: List<BranchDTO>
    )

    data class BranchDTO(
        val name: String,
        val lastCommitSha: String
    )
}
