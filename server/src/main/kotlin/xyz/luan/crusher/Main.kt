package xyz.luan.crusher

import org.slf4j.LoggerFactory
import spark.kotlin.*
import xyz.luan.crusher.api.CronApi

private val logger = LoggerFactory.getLogger("main")

fun main() {
    logger.info("Connecting to DB...")
    DatabaseWrapper.init()

    logger.info("Igniting spark!")
    val http: Http = ignite()

    val port = getHerokuAssignedPort()
    logger.info("Binding to port $port")
    http.port(port)

    routes(http)
}

private fun getHerokuAssignedPort(): Int {
    val env = ProcessBuilder().environment()
    return env["PORT"]?.let { Integer.parseInt(it) } ?: 4567
}

private fun routes(http: Http) {
    http.get("/hello") { "Hello Spark Kotlin/Heroku!" }

    CronApi.routes(http)
}

