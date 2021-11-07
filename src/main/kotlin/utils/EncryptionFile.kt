package utils

import data.db.entity.DBUser
import data.db.entity.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

private const val NAME_DB = "data.d"
private const val NAME_BIN = "data.bin"
private const val AES = "AES_128/CBC/NOPADDING"

fun databaseChecker(key: Key): Boolean {
    val file = File(NAME_BIN)
    if (!file.exists()) {
        // если файл не существует, то key - кодовая фраза для будущих данных
        return true
    }
    decrypt(key)
    var res = false
    try {
        val db = Database.connect("jdbc:sqlite:${NAME_DB}", "org.sqlite.JDBC")

        transaction(db) {
            addLogger(StdOutSqlLogger)
            if (SchemaUtils.checkCycle(UserTable) && DBUser.findById(0)!!.username == "ADMIN") {
                res = true
            }
        }
    } catch (e: Exception) {
        File(NAME_DB).delete()
        e.printStackTrace()
    }
    return res
}

fun getKey(password: String): Key {
    return SecretKeySpec(password.toByteArray(), "MD2")
}

fun encrypt(key: Key) {
    try {
        val des = Cipher.getInstance(AES)
        des.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key.encoded, AES))
        val enc = File(NAME_BIN).outputStream()
        val dec = File(NAME_DB).readBytes()
        enc.write(des.doFinal(dec))
        File(NAME_DB).delete()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun decrypt(key: Key) {
    try {
        val des = Cipher.getInstance(AES)
        des.init(Cipher.DECRYPT_MODE, key)
        val enc = File(NAME_BIN).readBytes()
        val dec = File(NAME_DB).outputStream()
        dec.write(des.doFinal(enc))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}