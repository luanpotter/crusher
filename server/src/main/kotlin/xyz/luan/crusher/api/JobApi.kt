package xyz.luan.crusher.api

import spark.kotlin.Http
import spark.kotlin.halt
import xyz.luan.crusher.CronWrapper
import xyz.luan.crusher.FirebaseWrapper
import xyz.luan.crusher.model.Cron
import xyz.luan.crusher.model.Db
import java.time.LocalDateTime

object JobApi {
    fun routes(http: Http) {
        http.get("/jobs") {
            validateToken(request)
            val crons = Db.allCrons()
            val currentTime = LocalDateTime.now().withMinute(0).withSecond(0)
            val amount = crons.filter {
                val shouldRun = CronWrapper.isNow(it.cronString!!, currentTime)
                if (shouldRun) runCron(it)
                shouldRun
            }.size
            "Successfully ran $amount crons"
        }
    }

    private fun runCron(cron: Cron) {
        print("Running cron ${cron.name} for ${cron.userEmail}")
        FirebaseWrapper.sendPush(cron.userDeviceToken!!, cron.pushTitle!!, cron.pushText!!)
    }

    private fun validateToken(request: spark.Request) {
        val token = request.queryParams("token") ?: throw halt(403, "Sorry, token query param required")
        if (token != getToken()) throw halt(403, "Invalid token provided: $token")
    }

    private fun getToken(): String {
        val env = ProcessBuilder().environment()
        return env["SECRET_KEY"] ?: "secret-token-local"
    }
}