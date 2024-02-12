# Github Pro
API for more concise formatting of Github API

### Technologies

&nbsp;&nbsp;&nbsp;&nbsp; Kotlin, Spring Boot 3, Github API

## API

Each endpoint might require an access token to perform requests successfully in case the limit count of daily requests was exceeded, otherwise it will return 403 error response.

**Endpoints**
<br>  
| HTTP Method | Endpoint | Description | Link |
| --- | --- | --- | --- | 
| `GET` | `api/v1/repos?username={username}` | Get repositories for given user | [Link](https://github.com/netspie/github-pro/blob/main/src/main/kotlin/com/netspie/githubpro/features/userRepositories/UserRepositoriesController.kt) |

&nbsp;&nbsp;&nbsp;&nbsp;  
## API Details

### Responses  

The API normally returns JSON in the response body. In case of some errors it will return HTTP status code (500, 403, 404). The response body JSON might be is a unique object if it's a GET request, and/or it might be an error object depending on the request was successfully processed.

&nbsp;&nbsp;&nbsp;&nbsp; *Result Fields*

- object
  - messages - array of objects
    - object
      - status - integer
      - message - string
      
*Example:*
```
HTTP/1.1 400 Bad Request
{
    "status": 403,
    "message": "Daily unauthorized requests count limit has exceeded",
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
&nbsp;&nbsp;&nbsp;&nbsp; Object containing a repositories array

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
