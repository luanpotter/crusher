package xyz.luan.crusher.api

import com.google.gson.Gson
import spark.kotlin.Http
import spark.kotlin.RouteHandler
import spark.kotlin.halt
import xyz.luan.crusher.model.Cron
import xyz.luan.crusher.model.Crons
import xyz.luan.crusher.model.Db

private val json = Gson()

private fun RouteHandler.parseCron() = json.fromJson(request.body(), Cron::class.java)

object CronApi {
    fun routes(http: Http) {
        http.get("/crons") {
            val email = "foo@bar.com"
            val crons = Db.listCrons(email)
            json.toJson(crons)
        }
        http.post("/crons") {
            print("----- ${request.body()}")
            val cron = parseCron()
            print("----- $cron")
            val email = "foo@bar.com"
            if (cron.userEmail != email) halt(403, "You can only created crons for yourself!")
            // TODO validate cron
            // TODO create it
            json.toJson(cron)
        }
    }
}