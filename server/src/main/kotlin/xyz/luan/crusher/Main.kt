package xyz.luan.crusher

import spark.kotlin.*

private fun getHerokuAssignedPort(): Int {
    val env = ProcessBuilder().environment()
    return env["PORT"]?.let { Integer.parseInt(it) } ?: 4567
}

fun main() {
    val http: Http = ignite()
    val port = getHerokuAssignedPort()
    print("foo bar $port")
    port(port)

    http.get("/hello") { "Hello Spark Kotlin/Heroku!" }
}