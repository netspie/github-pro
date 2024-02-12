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
