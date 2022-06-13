# Kotlin Fake API

Simple API returning the same data as
[JSON Placeholder](https://jsonplaceholder.typicode.com/)
built using Kotlin, the [`Ktor`](https://ktor.io/) framework, the
[`jackson`](https://github.com/FasterXML/jackson-module-kotlin) library for
parsing XML data and the [`Exposed`](https://github.com/JetBrains/Exposed) library as a database ORM.

Only a subset of the original relations is currently implemented.

## Usage

To run the application in a Docker container:

```bash
cd docker
docker-compose up
```

The server runs by default on port `8080`, but the port can be set using the `PORT` environment variable.
