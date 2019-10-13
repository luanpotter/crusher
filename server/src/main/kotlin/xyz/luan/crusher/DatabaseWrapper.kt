package xyz.luan.crusher

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils

object DatabaseWrapper {
    fun init() {
        val env: Map<String, String> = ProcessBuilder().environment()
        val connectionString: String = env["JDBC_DATABASE_URL"] ?: throw RuntimeException("Must provide connection string!")
        Database.connect(connectionString)
        SchemaUtils.create(Crons)
    }
}