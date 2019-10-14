package xyz.luan.crusher.model

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction
import xyz.luan.crusher.model.DbCrons

object Db {
    private val db: Database by lazy {
        val env = ProcessBuilder().environment()
        val (connectionString, driver) = env["JDBC_DATABASE_URL"]?.let {
            Pair(it, "org.postgresql.Driver")
        } ?: Pair("jdbc:h2:mem:test", "org.h2.Driver")
        Database.connect(connectionString, driver)
    }

    fun init() {
        print("Started database: ${db.url}")
        t { SchemaUtils.create(DbCrons) }
    }

    fun listCrons(userEmail: String): List<DbCron> {
        return t { DbCron.find { DbCrons.userEmail.eq(userEmail) }.toList() }
    }

    private fun <T> t(statement: Transaction.() -> T): T {
        return transaction(db, statement)
    }
}