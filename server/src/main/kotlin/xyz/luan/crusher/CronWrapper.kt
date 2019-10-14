package xyz.luan.crusher

import com.cronutils.model.CronType.UNIX
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.time.ExecutionTime
import com.cronutils.parser.CronParser
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

object CronWrapper {
    private val parser by lazy {
        CronParser(CronDefinitionBuilder.instanceDefinitionFor(UNIX))
    }

    fun isValid(cronString: String): Boolean {
        return try {
            val cron = parser.parse(complete(cronString))?.validate()
            cron != null
        } catch (ex: IllegalArgumentException) {
            print("Invalid cron provided! $cronString")
            false
        }
    }

    fun isNow(cronString: String, currentTime: LocalDateTime): Boolean {
        val cron = parser.parse(complete(cronString))
        val now = currentTime.atZone(ZoneOffset.UTC)
        return ExecutionTime.forCron(cron).isMatch(now)
    }

    private fun complete(cronString: String): String = "0 $cronString"
}