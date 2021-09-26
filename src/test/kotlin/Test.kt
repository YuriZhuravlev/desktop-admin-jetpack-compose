import utils.Encryption
import utils.EncryptionPassword
import kotlin.test.Test

class Test {
    private val encryption: Encryption by lazy {
        EncryptionPassword()
    }

    @Test
    fun t0() {
        val pass = "admin"
        val passEnc = encryption.encryptionPassword(pass)
        val passDec = encryption.decryptionPassword(passEnc)
        println("$pass -> $passEnc -> $passDec")
        assert(pass == passDec)
    }


    @Test
    fun t1() {
        val pass = "qwerty"
        val passEnc = encryption.encryptionPassword(pass)
        val passDec = encryption.decryptionPassword(passEnc)
        println("$pass -> $passEnc -> $passDec")
        assert(pass == passDec)
    }


    @Test
    fun t2() {
        val pass = "123456789"
        val passEnc = encryption.encryptionPassword(pass)
        val passDec = encryption.decryptionPassword(passEnc)
        println("$pass -> $passEnc -> $passDec")
        assert(pass == passDec)
    }


    @Test
    fun t3() {
        val pass = "sdf5gb484ssdff"
        val passEnc = encryption.encryptionPassword(pass)
        val passDec = encryption.decryptionPassword(passEnc)
        println("$pass -> $passEnc -> $passDec")
        assert(pass == passDec)
    }


    @Test
    fun t4() {
        val pass = "98jk,61sdf"
        val passEnc = encryption.encryptionPassword(pass)
        val passDec = encryption.decryptionPassword(passEnc)
        println("$pass -> $passEnc -> $passDec")
        assert(pass == passDec)
    }


    @Test
    fun t5() {
        val pass = "46jyn$&W*vdf"
        val passEnc = encryption.encryptionPassword(pass)
        val passDec = encryption.decryptionPassword(passEnc)
        println("$pass -> $passEnc -> $passDec")
        assert(pass == passDec)
    }


    @Test
    fun t6() {
        val pass = "dsfbbf"
        val passEnc = encryption.encryptionPassword(pass)
        val passDec = encryption.decryptionPassword(passEnc)
        println("$pass -> $passEnc -> $passDec")
        assert(pass == passDec)
    }


    @Test
    fun t7() {
        val pass = "sbg8f\\b7bgf"
        val passEnc = encryption.encryptionPassword(pass)
        val passDec = encryption.decryptionPassword(passEnc)
        println("$pass -> $passEnc -> $passDec")
        assert(pass == passDec)
    }


    @Test
    fun t8() {
        val pass = "][ojh,k"
        val passEnc = encryption.encryptionPassword(pass)
        val passDec = encryption.decryptionPassword(passEnc)
        println("$pass -> $passEnc -> $passDec")
        assert(pass == passDec)
    }


    @Test
    fun t9() {
        val pass = "ADMIN"
        val passEnc = encryption.encryptionPassword(pass)
        val passDec = encryption.decryptionPassword(passEnc)
        println("$pass -> $passEnc -> $passDec")
        assert(pass == passDec)
    }


    @Test
    fun t10() {
        val pass = "65AJvdfAKScvx"
        val passEnc = encryption.encryptionPassword(pass)
        val passDec = encryption.decryptionPassword(passEnc)
        println("$pass -> $passEnc -> $passDec")
        assert(pass == passDec)
    }


    @Test
    fun t11() {
        val pass = "QWERTY"
        val passEnc = encryption.encryptionPassword(pass)
        val passDec = encryption.decryptionPassword(passEnc)
        println("$pass -> $passEnc -> $passDec")
        assert(pass == passDec)
    }


    @Test
    fun t12() {
        val pass = "7q0SAKvdf"
        val passEnc = encryption.encryptionPassword(pass)
        val passDec = encryption.decryptionPassword(passEnc)
        println("$pass -> $passEnc -> $passDec")
        assert(pass == passDec)
    }
}