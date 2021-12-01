package utils

import com.sun.jna.platform.win32.Advapi32Util
import com.sun.jna.platform.win32.WinReg
import java.awt.Toolkit
import java.io.File
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.X509EncodedKeySpec

object SignatureVerification {

    private const val KEY = "Software\\Журавлев"

    /**
     * 1) собирается информация о компьютере:
     *  имя пользователя;
     *  имя компьютера;
     *  путь к папке с ОС Windows;
     *  путь к папке с системными файлами ОС Windows;
     *  ширина экрана;
     *  набор дисковых устройств;
     *  объем диска, на котором установлена программа.
     *
     *  2) вычисляется хеш-значение этой информации
     *
     *  3) считывается подпись из указанного выше раздела реестра, которая проверяется с помощью открытого ключа пользователя
     */
    suspend fun signatureVerification(): Boolean {
        return try {
            val data = getData()
            // TODO delete
            println(data)

            val (signature, key) = getDigitalSignature()

            sign(
                publicKeyEncoded = key,
                data = data.toByteArray(),
                digitalSignature = signature
            )
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    /**
     * @return (digitalSignature, publicKeyEncoded)
     */
    private fun getDigitalSignature(): Pair<ByteArray, ByteArray> {
        val signature = Advapi32Util.registryGetBinaryValue(WinReg.HKEY_CURRENT_USER, KEY, "Signature")
        val key = Advapi32Util.registryGetBinaryValue(WinReg.HKEY_CURRENT_USER, KEY, "Key")
        return signature to key
    }

    private fun sign(publicKeyEncoded: ByteArray, data: ByteArray, digitalSignature: ByteArray): Boolean {
        val signature = Signature.getInstance("SHA256withRSA")

        val ks = X509EncodedKeySpec(publicKeyEncoded)
        val publicKey = KeyFactory.getInstance("RSA")
            .generatePublic(ks)
        signature.initVerify(publicKey)
        signature.update(data)
        return signature.verify(digitalSignature)
    }

    fun getData(): String {
        val data = StringBuilder()
        val env = System.getenv()
        // имя пользователя
        data.append(env["USERNAME"])
        // имя компьютера
        data.append(env["COMPUTERNAME"])
        // путь к папке с ОС Windows
        data.append(env["windir"])
        // путь к папке с системными файлами ОС Windows
        data.append(env["SystemRoot"])
        // ширина экрана
        data.append(Toolkit.getDefaultToolkit().screenSize.width.toString())
        // набор дисковых устройств
        for (item in File.listRoots())
            data.append(item.path)
        // объем диска, на котором установлена программа
        data.append(File(ViewModel::class.java.protectionDomain.codeSource.location.toURI()).totalSpace)
        return data.toString()
    }
}