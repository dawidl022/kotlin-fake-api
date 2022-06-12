package io.github.dawidl022

import io.github.dawidl022.models.Album
import io.github.dawidl022.models.Albums
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*
import io.github.dawidl022.plugins.*
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking
import org.eclipse.jetty.http.HttpHeader

class ApplicationTest {
    private fun configure(testBuilder: ApplicationTestBuilder) {
        testBuilder.application {
            configureRouting()
            configureSerialization()
        }
    }

    @Test
    fun testRoot() = testApplication {
        configure(this)
        client.get("/").apply {
            assertEquals(HttpStatusCode.NotFound, status)
        }
    }

    @Test
    fun testResourceGet() = testApplication {
        configure(this)
        client.get("/album/1").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(
                """{"id":1,"userId":1,"title":"quidem molestiae enim"}""", bodyAsText()
            )
        }
    }

    @Test
    fun testResourcePost() = testApplication {
        configure(this)
        client.post("/album") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody("""{"id":1000,"userId":1,"title":"quidem molestiae enim"}""")
        }.apply {
            assertEquals(HttpStatusCode.Accepted, status)
        }
    }
}
