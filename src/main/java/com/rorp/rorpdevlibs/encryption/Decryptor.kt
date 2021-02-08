package com.rorp.rorpdevlibs.encryption

import com.rorp.rorpdevlibs.constant.Constants
import org.jetbrains.annotations.NotNull
import java.io.IOException
import java.nio.charset.Charset
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.*
import javax.crypto.spec.GCMParameterSpec

/*
         ____                             _
        |  _ \  ___  ___ _ __ _   _ _ __ | |_ ___  _ __
        | | | |/ _ \/ __| '__| | | | '_ \| __/ _ \| '__|
        | |_| |  __/ (__| |  | |_| | |_) | || (_) | |
        |____/ \___|\___|_|   \__, | .__/ \__\___/|_|
                          |___/|_|

 */
/**
 * @author Matt Dev
 * @since 2021.02.03
 */
class Decryptor() {
    private lateinit var keyStore : KeyStore

    init {
        try {
            initKeyStore()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(
        KeyStoreException::class,
        CertificateException::class,
        NoSuchAlgorithmException::class,
        IOException::class
    )
    private fun initKeyStore(){
        keyStore = KeyStore.getInstance(Constants.ANDROID_KEY_STORE).apply { load(null) }
    }

    @Throws(
        UnrecoverableEntryException::class,
        NoSuchAlgorithmException::class,
        KeyStoreException::class,
        NoSuchProviderException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IOException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class,
        InvalidAlgorithmParameterException::class
    )
    fun decryptData(@NotNull alias : String, @NotNull encryptData : ByteArray, @NotNull encryptionIv : ByteArray) : String {
        val cipher  = Cipher.getInstance(Constants.TRANSFORMATION)
        val spec     = GCMParameterSpec(128, encryptionIv)
        cipher.init(Cipher.DECRYPT_MODE, getSecreteKey(alias), spec)
        return String(cipher.doFinal(encryptData), Charset.forName("UTF-8"))
    }

    @NotNull
    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidAlgorithmParameterException::class
    )
    private fun getSecreteKey(@NotNull alias : String) : SecretKey {
        val key = keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry
        return key.secretKey
    }
}