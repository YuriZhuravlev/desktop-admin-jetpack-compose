package utils

interface Encryption {
    /**
     * Зашифровать пароль
     */
    fun encryptionPassword(password: String): ByteArray

    /**
     * Расшифровать пароль
     */
    fun decryptionPassword(encryptedPassword: ByteArray): String
}