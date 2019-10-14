package xyz.luan.crusher.model

import com.google.gson.annotations.JsonAdapter
import com.rnett.exposedgson.ExposedTypeAdapter
import org.jetbrains.exposed.dao.*

object Crons : IntIdTable() {
    val name = varchar("name", length = 255).index()
    val userEmail = varchar("user_email", length = 255).index()

    val cronString = varchar("cron_string", length = 255)
    val pushTitle = text("push_title")
    val pushText = text("push_text")
}

@JsonAdapter(ExposedTypeAdapter::class)
class Cron(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Cron>(Crons)

    var name by Crons.name
    var userEmail by Crons.userEmail
    var cronString by Crons.cronString
    var pushTitle by Crons.pushTitle
    var pushText by Crons.pushText
}