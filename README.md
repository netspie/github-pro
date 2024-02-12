# Github Pro
API for more concise formatting of Github API

### Technologies

&nbsp;&nbsp;&nbsp;&nbsp; Kotlin, Spring Boot 3, Github API

## API

Each endpoint might require an access token to perform requests successfully in case the rate limit exceeded, otherwise it will return 403 error response. [More Info](https://docs.github.com/en/rest/using-the-rest-api/rate-limits-for-the-rest-api?apiVersion=2022-11-28).

**Endpoints**
<br>  
| HTTP Method | Endpoint | Description | Link |
| --- | --- | --- | --- | 
| `GET` | `api/v1/repos?username={username}` | Get repositories for given user | [Link](https://github.com/netspie/github-pro/blob/main/src/main/kotlin/com/netspie/githubpro/features/userRepositories/UserRepositoriesController.kt) |

&nbsp;&nbsp;&nbsp;&nbsp;  
## API Details

### Responses  

The API normally returns JSON in the response body. In case of some errors it will return HTTP status code (500, 403, 404). The response body with JSON might be is a unique object if it's a GET request or an error object if the request was not successfully processed.

&nbsp;&nbsp;&nbsp;&nbsp; *Result Fields*

- object
  - messages - array of objects
    - object
      - status - integer
      - message - string
      
*Example:*
```
HTTP/1.1 403 Forbidden
{
    "status": 403,
    "message": "403 rate limit exceeded",
}
```

### API Endpoint - Get user repositories

| HTTP Method | Endpoint | Description | Link |
| --- | --- | --- | --- | 
| `GET` | `api/v1/repos?username={username}` | Get repositories for given user | [Link](https://github.com/netspie/github-pro/blob/main/src/main/kotlin/com/netspie/githubpro/features/userRepositories/UserRepositoriesController.kt) |

#### Description

Get repositories for a specific user which are not forks.

#### Success Response

##### *200*
&nbsp;&nbsp;&nbsp;&nbsp; Object containing repositories array

&nbsp;&nbsp;&nbsp;&nbsp; *Fields*
- object
  - repositories - array of objects
    - object -
      - name - string; name of the repository
      - login - string; login of the owner of the repository
      - branches - array of objects
        - object
          - name - string; name of the branch
          - lastCommitSha - string

&nbsp;&nbsp;&nbsp;&nbsp; *Example*

```json
{
  "repositories": [
    {
      "name": "netspie/auctions-api",
      "login": "netspie",
      "branches": [
        {
          "name": "main",
          "lastCommitSha": "7124e5a4e0ee579f438239946cea084f976c0f71"
        },
        {
          "name": "postgresql-repo",
          "lastCommitSha": "5cb07ebeb4a6d01a38827f883cf79fa7b8109783"
        }
      ]
    },
    {
      "name": "netspie/blazor-app-template",
      "login": "netspie",
      "branches": [
        {
          "name": "main",
          "lastCommitSha": "01c3837fba9142e1a01b06acce1f25325a65fb14"
        }
      ]
    },
}
