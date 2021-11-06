package utils

//  8. Шифрование гаммированием
//  (длина блока – 1 байт,
//  блоки гаммы формируются по правилу
//  Gi+1=A*Gi+C { mod 256 },
//  где A=5, C=3, G0=1).

/*
4.	Гаммирование. Шифрование:
∀ i, 0≤i≤n-1 Ci=Pi ⊕ Gi, где
•	P={P0, P1, … , Pi, … , Pn-1} – открытый текст;
•	n – длина открытого текста;
•	C={C0, C1, … , Ci, … , Cn-1} – шифротекст;
•	G={G0, G1, … , Gi, … , Gn-1}  – гамма шифра;
•	⊕ - операция поразрядного сложения по модулю 2.
Расшифрование:
∀ i, 0≤i≤n-1 Pi=Ci ⊕ Gi
 */

class EncryptionPassword : Encryption {

    override fun encryptionPassword(password: String): ByteArray {
        val passwordBytes = password.toByteArray(Charsets.UTF_8)
        val key = gammaKey(passwordBytes.size)
        return ByteArray(passwordBytes.size) {
            passwordBytes[it].toUByte().xor(key[it]).toByte()
        }
    }

    override fun decryptionPassword(encryptedPassword: ByteArray): String {
        val key = gammaKey(encryptedPassword.size)
        return ByteArray(encryptedPassword.size) {
            encryptedPassword[it].toUByte().xor(key[it]).toByte()
        }.toString(Charsets.UTF_8)
    }

    /**
     * Генерация гамма-шифра заданной длины
     */
    private fun gammaKey(length: Int): Array<UByte> {
        val a = 5u
        val c = 3u
        var g: UByte = 1u
        return Array(length) {
            if (it != 0)
                g = (a * g + c).toUByte()
            g
        }
    }
}