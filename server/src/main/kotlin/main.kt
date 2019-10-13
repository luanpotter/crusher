import spark.kotlin.*

fun main() {
    val http: Http = ignite()

    http.get("/hello") { "Hello Spark Kotlin/Heroku!" }
}