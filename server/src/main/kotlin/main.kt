import spark.kotlin.*

fun main() {
    val http: Http = ignite()
    port(getHerokuAssignedPort())

    http.get("/hello") { "Hello Spark Kotlin/Heroku!" }
}

fun getHerokuAssignedPort(): Int {
    val env = ProcessBuilder().environment()
    return env["PORT"]?.let { Integer.parseInt(it) } ?: 4567
}