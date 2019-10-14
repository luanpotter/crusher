package xyz.luan.crusher.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import spark.kotlin.halt
import xyz.luan.crusher.CronWrapper
import xyz.luan.crusher.nullOrEmpty


object DbCrons : IntIdTable("crons") {
    val name = varchar("name", length = 255).index()
    val userEmail = varchar("user_email", length = 255).index()
    val userDeviceToken = varchar("user_device_token", length = 255).index()

    val cronString = varchar("cron_string", length = 255)
    val pushTitle = text("push_title")
    val pushText = text("push_text")
}

class DbCron(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DbCron>(DbCrons)

    var name by DbCrons.name
    var userEmail by DbCrons.userEmail
    var cronString by DbCrons.cronString
    var pushTitle by DbCrons.pushTitle
    var pushText by DbCrons.pushText
    var userDeviceToken by DbCrons.userDeviceToken

    fun to(): Cron {
        return Cron(
            id = id.value,
            name = name,
            userEmail = userEmail,
            cronString = cronString,
            pushTitle = pushTitle,
            pushText = pushText,
            userDeviceToken = userDeviceToken
        )
    }
}

data class Cron constructor(
    val id: Int?,
    val name: String?,
    val userEmail: String?,
    val cronString: String?,
    val pushTitle: String?,
    val pushText: String?,
    val userDeviceToken: String?
) {
    fun validate() {
        if (nullOrEmpty(name)) throw halt(422, "Field 'name' is required")
        if (nullOrEmpty(userEmail)) throw halt(422, "Field 'userEmail' is required")
        if (nullOrEmpty(pushTitle)) throw halt(422, "Field 'pushTitle' is required")
        if (nullOrEmpty(pushText)) throw halt(422, "Field 'pushText' is required")
        if (nullOrEmpty(userDeviceToken)) throw halt(422, "Field 'userDeviceToken' is required")

        if (nullOrEmpty(cronString)) {
            throw halt(422, "Field 'cronString' is required")
        } else if (!CronWrapper.isValid(cronString!!)) {
            throw halt(422, "Field 'cronString' must be a valid cron.")
        }
    }

    fun save(): Cron {
        val cron = this
        val dbCron = DbCron.new {
            name = cron.name!!
            userEmail = cron.userEmail!!
            cronString = cron.cronString!!
            pushTitle = cron.pushTitle!!
            pushText = cron.pushText!!
            userDeviceToken = cron.userDeviceToken!!
        }
        return dbCron.to()
    }
}