package xyz.luan.crusher.api

import com.google.gson.Gson
import spark.kotlin.Http
import spark.kotlin.RouteHandler
import spark.kotlin.halt
import xyz.luan.crusher.model.Cron
import xyz.luan.crusher.model.Crons

private val json = Gson()

private fun RouteHandler.parseCron() = json.fromJson(request.body(), Cron::class.java)

object CronApi {
    fun routes(http: Http) {
        http.get("/crons") {
            val email = "foo@bar.com"
            val crons = Cron.find { Crons.userEmail.eq(email) }.toList()
            json.toJson(crons)
        }
        http.post("/crons") {
            val cron = parseCron()
            val email = "foo@bar.com"
            if (cron.userEmail != email) halt(403, "You can only created crons for yourself!")
            // TODO validate cron
            // TODO create it
            json.toJson(cron)
        }
    }
}