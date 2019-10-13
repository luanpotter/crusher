package xyz.luan.crusher

import spark.kotlin.*
import xyz.luan.crusher.api.CronApi
import xyz.luan.crusher.model.Db

fun main() {
    print("Connecting to DB...")
    Db.init()

    print("Igniting spark!")
    val http: Http = ignite()

    val port = getHerokuAssignedPort()
    print("Binding to port $port")
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

