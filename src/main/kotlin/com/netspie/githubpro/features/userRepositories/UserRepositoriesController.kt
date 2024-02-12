package com.netspie.githubpro.features.userRepositories

import com.netspie.githubpro.features.shared.ResultT
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
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
        username: String) : ResponseEntity<Any> =
        getAllUserRepositoriesQueryHandler.execute(
            GetAllUserRepositoriesQuery(username, authorizationToken)
        ).toApiResponse()

}

data class ApiError(
    val status: Int,
    val message: String
)

fun<T> ResultT<T>.toApiResponse(): ResponseEntity<Any> {
    if (!this.isSuccess) {
        val message = this.messages.joinToString(separator = ". ") { it.content }
        val errorCode = message.takeWhile { it != ' ' }.toIntOrNull() ?: 404

        return ResponseEntity(
            ApiError(errorCode, message), HttpStatusCode.valueOf(errorCode))
    }

    return ResponseEntity.ok(this.value)
}
