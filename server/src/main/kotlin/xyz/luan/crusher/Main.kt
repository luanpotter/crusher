package xyz.luan.crusher

import spark.kotlin.*

object Main {
    private fun getHerokuAssignedPort(): Int {
        val env = ProcessBuilder().environment()
        return env["PORT"]?.let { Integer.parseInt(it) } ?: 4567
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val http: Http = ignite()
        port(getHerokuAssignedPort())

        http.get("/hello") { "Hello Spark Kotlin/Heroku!" }
    }
}