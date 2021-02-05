package com.rorp.rorpdevlibs.encryption

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import org.jetbrains.annotations.NotNull
import java.io.IOException
import java.nio.charset.Charset
import java.security.*
import javax.crypto.*

/*
         _____        ____                  _
        | ____|_ __  / ___|_ __ _   _ _ __ | |_ ___  _ __
        |  _| | '_ \| |   | '__| | | | '_ \| __/ _ \| '__|
        | |___| | | | |___| |  | |_| | |_) | || (_) | |
        |_____|_| |_|\____|_|   \__, | .__/ \__\___/|_|
                                |___/|_|
 */
/**
 * @author Matt Dev
 * @since 2021.02.03
 */
class Encryptor {

    private lateinit var encryption : ByteArray
    private lateinit var iv : ByteArray

    @Throws(
        UnrecoverableEntryException::class,
        NoSuchAlgorithmException::class,
        KeyStoreException::class,
        NoSuchProviderException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IOException::class,
        InvalidAlgorithmParameterException::class,
        SignatureException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    fun encryptText(@NotNull alias : String, @NotNull textToEncrypt : String)  : ByteArray{
        val cipher = Cipher.getInstance(Constants.TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecreteKey(alias))

        iv = cipher.iv
        encryption = cipher.doFinal(cipher.doFinal(textToEncrypt.toByteArray(Charset.forName("UTF-8"))))

        return encryption
    }

    @NotNull
    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchProviderException::class,
        InvalidAlgorithmParameterException::class
    )
    private fun getSecreteKey(@NotNull alias : String) : SecretKey{
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, Constants.ANDROID_KEY_STORE)

        keyGenerator.init(KeyGenParameterSpec.Builder(alias,
            KeyProperties.PURPOSE_ENCRYPT or  KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        )

        return keyGenerator.generateKey()
    }

    fun getEncryption() : ByteArray {
        return encryption
    }

    fun getIv() : ByteArray{
        return iv
    }
}