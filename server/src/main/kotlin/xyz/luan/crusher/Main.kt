package xyz.luan.crusher

import org.slf4j.LoggerFactory
import spark.kotlin.*

private val logger = LoggerFactory.getLogger("main")

private fun getHerokuAssignedPort(): Int {
    val env = ProcessBuilder().environment()
    return env["PORT"]?.let { Integer.parseInt(it) } ?: 4567
}

fun main() {
    logger.info("Igniting spark!")
    val http: Http = ignite()

    val port = getHerokuAssignedPort()
    logger.info("Binding to port $port")
    http.port(port)

    http.get("/hello") { "Hello Spark Kotlin/Heroku!" }
}

