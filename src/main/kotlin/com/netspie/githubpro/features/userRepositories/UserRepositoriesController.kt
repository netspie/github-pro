package com.netspie.githubpro.features.userRepositories

import com.netspie.githubpro.features.shared.ResultT
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/repos")
class UserRepositoriesController(
    private val getAllUserRepositoriesQueryHandler: GetAllUserRepositoriesQueryHandler
) {
    @GetMapping
    fun getAllWithToken(
        @RequestHeader("Authorization", required = false) authorizationToken: String?,
        username: String) =
        getAllUserRepositoriesQueryHandler.execute(
            GetAllUserRepositoriesQuery(username, authorizationToken)
        ).toApiResponse()

}

data class ApiError(
    val status: Int,
    val message: String
)

fun<T> ResultT<T>.toApiResponse(): Any? {
    if (!this.isSuccess)
        return ApiError(404, this.messages.joinToString(separator = ". ") { it.content })

    return this.value
}
