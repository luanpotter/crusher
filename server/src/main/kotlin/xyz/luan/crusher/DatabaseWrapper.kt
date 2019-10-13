package xyz.luan.crusher

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import xyz.luan.crusher.model.Crons

object DatabaseWrapper {
    fun init() {
        val env = ProcessBuilder().environment()
        val (connectionString, driver) = env["JDBC_DATABASE_URL"]?.let {
            Pair(it, "org.postgresql.Driver")
        } ?: Pair("jdbc:h2:mem:test", "org.h2.Driver")
        Database.connect(connectionString, driver)

        SchemaUtils.create(Crons)
    }
}