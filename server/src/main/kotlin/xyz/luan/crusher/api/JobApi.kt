package xyz.luan.crusher.api

import spark.kotlin.Http
import xyz.luan.crusher.CronWrapper
import xyz.luan.crusher.FirebaseWrapper
import xyz.luan.crusher.model.Cron
import xyz.luan.crusher.model.Db
import java.time.LocalDateTime

object JobApi {
    fun routes(http: Http) {
        http.get("/jobs") {
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
}