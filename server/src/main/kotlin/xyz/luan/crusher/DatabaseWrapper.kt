package xyz.luan.crusher

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import xyz.luan.crusher.model.Crons

object DatabaseWrapper {
    val db: Database by lazy {
        val env = ProcessBuilder().environment()
        val (connectionString, driver) = env["JDBC_DATABASE_URL"]?.let {
            Pair(it, "org.postgresql.Driver")
        } ?: Pair("jdbc:h2:mem:test", "org.h2.Driver")
        Database.connect(connectionString, driver)
    }

    fun init() {
        print("Started database: ${db.url}")
        transaction(db) { SchemaUtils.create(Crons) }
    }
}