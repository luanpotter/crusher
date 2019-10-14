package xyz.luan.crusher.model

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

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
        transaction(db) { SchemaUtils.create(DbCrons) }
    }

    fun allCrons(): List<Cron> {
        return transaction(db) { DbCron.all().map { it.to() }.toList() }
    }

    fun listCrons(userEmail: String): List<Cron> {
        return transaction(db) { DbCron.find { DbCrons.userEmail.eq(userEmail) }.map { it.to() }.toList() }
    }

    fun createCron(cron: Cron): Cron {
        return transaction(db) { cron.save() }
    }

    fun findDbCron(id: Int): DbCron? {
        return transaction(db) { DbCron.findById(id) }
    }

    fun deleteCron(dbCron: DbCron) {
        return transaction(db) { dbCron.delete() }
    }
}