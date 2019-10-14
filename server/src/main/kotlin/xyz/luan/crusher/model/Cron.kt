package xyz.luan.crusher.model

import org.jetbrains.exposed.dao.*

object DbCrons : IntIdTable("crons") {
    val name = varchar("name", length = 255).index()
    val userEmail = varchar("user_email", length = 255).index()

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

    fun to(): Cron {
        return Cron(
            id = id.value,
            name = name,
            userEmail = userEmail,
            cronString = cronString,
            pushTitle = pushTitle,
            pushText = pushText
        )
    }
}

data class Cron constructor(
    val id: Int,
    val name: String,
    val userEmail: String,
    val cronString: String,
    val pushTitle: String,
    val pushText: String
) {
    fun save(): Cron {
        val cron = this;
        val dbCron = DbCron.new {
            name = cron.name;
            userEmail = cron.userEmail;
            cronString = cron.cronString;
            pushTitle = cron.pushTitle;
            pushText = cron.pushText;
        }
        return dbCron.to()
    }
}