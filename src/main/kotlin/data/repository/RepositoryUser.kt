package data.repository

import data.Resource
import data.db.DAO
import data.db.DAOImpl
import data.db.entity.DBUser
import data.model.UIUser
import utils.Encryption
import utils.EncryptionPassword
import java.io.File
import java.security.Key

object RepositoryUser {
    private val dao: DAO = DAOImpl
    private val encryption: Encryption = EncryptionPassword()

    suspend fun getUserByName(name: String): Resource<UIUser?> {
        return try {
            Resource.success(dao.getUserByName(name)?.toUIUser())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(e)
        }
    }

    suspend fun getUsers(): Resource<List<UIUser>> {
        return try {
            Resource.success(dao.getUsers().map { it.toUIUser() })
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(e)
        }
    }

    suspend fun editPassword(userId: Int, newPassword: String): UIUser? =
        dao.editPassword(userId, encryption.encryptionPassword(newPassword))?.toUIUser()

    suspend fun editIsBlocked(id: Int, fl: Boolean): Resource<UIUser?> =
        try {
            Resource.success(dao.editIsBlocked(id, fl)?.toUIUser())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(e)
        }

    suspend fun editStrongPassword(id: Int, fl: Boolean): Resource<UIUser?> =
        try {
            Resource.success(dao.editStrongPassword(id, fl)?.toUIUser())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(e)
        }

    suspend fun addUser(name: String, strongPassword: Boolean): Resource<UIUser> {
        return try {
            Resource.success(dao.addUser(name, strongPassword).toUIUser())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(e)
        }
    }

    /**
     * Расшифровка пароля
     */
    fun decryptionPassword(user: UIUser): String = encryption.decryptionPassword(user.password)
}

fun DBUser.toUIUser() = UIUser(id.value, username, password, isBlocked, strongPassword)


private const val NAME_DB = "data.d"
private const val NAME_BIN = "data.bin"

fun databaseChecker(key: Key): Boolean {
    val file = File(NAME_BIN)
    if (!file.exists()) {
        return true
    }
    return if (key.encoded.contentEquals(decrypt(key))) {
        true
    } else {
        val file = File(NAME_DB)
        file.writeBytes(ByteArray(0))
        file.delete()
        false
    }
}

fun encrypt(key: Key) {
    try {
        key.encoded
        val enc = File(NAME_DB).readBytes()
        val dec = File(NAME_BIN).outputStream()
        dec.write(key.encoded.size)
        dec.write(key.encoded)
        dec.write(enc)
        File(NAME_DB).delete()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun decrypt(key: Key): ByteArray? {
    var pass: ByteArray? = null
    try {
        val enc = File(NAME_DB).outputStream()
        val dec = File(NAME_BIN).inputStream()
        val len = dec.read()
        pass = dec.readNBytes(len)
        enc.write(dec.readBytes())
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return pass
}