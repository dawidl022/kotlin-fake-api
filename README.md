# Kotlin Fake API

Simple API returning the same data as
[JSON Placeholder](https://jsonplaceholder.typicode.com/)
built using Kotlin, the [`Ktor`](https://ktor.io/) framework and the following libraries:
- [`jackson`](https://github.com/FasterXML/jackson-module-kotlin) for
  parsing XML data
- [`Exposed`](https://github.com/JetBrains/Exposed) library as a database ORM]
- [`graphql-kotlin`](https://github.com/ExpediaGroup/graphql-kotlin) for managing GraphQL endpoint

Only a subset of the original relations is currently implemented.

## Usage

To run the application in a Docker container:

```bash
cd docker
docker-compose up
```

The server runs by default on port `8080`, but the port can be set using the `PORT` environment variable.
