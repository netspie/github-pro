package com.netspie.githubpro.features.userRepositories

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
    fun getAll(
        @RequestHeader("Authorization", required = false) authorizationToken: String,
        username: String) =
        getAllUserRepositoriesQueryHandler.execute(
            GetAllUserRepositoriesQuery(username, authorizationToken)
        )
}
