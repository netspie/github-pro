package com.netspie.githubpro.features.shared

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class HttpClient(
    authorizationToken: String?) {

    val restTemplate: RestTemplate = RestTemplate()
     val headers: HttpHeaders = HttpHeaders()

    init {
        if (!authorizationToken.isNullOrEmpty()) {
            val tokenSplit = authorizationToken.split(' ').last
            headers.set("Authorization", "token $tokenSplit")
        }
    }

    inline fun<reified TResponse> get(url: String): TResponse? {
        val requestEntity = HttpEntity<TResponse>(headers)
        val responseEntity: ResponseEntity<TResponse> = restTemplate
            .exchange(url, HttpMethod.GET, requestEntity, TResponse::class.java)

        return responseEntity.body;
    }
}
