package com.netspie.githubpro.features.repositories

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/repositories")
class RepositoriesController(
    private val getAllUserRepositoriesQueryHandler: GetAllUserRepositoriesQueryHandler
) {
    @GetMapping
    fun getAll(username: String) =
        getAllUserRepositoriesQueryHandler.execute(
            GetAllUserRepositoriesQuery(username)
        )
}