package utils

interface EncryptionFile {
    fun encrypt(key: String)
    fun decrypt(key: String)
}