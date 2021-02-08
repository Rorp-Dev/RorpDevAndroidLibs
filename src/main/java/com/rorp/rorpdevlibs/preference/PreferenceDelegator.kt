package com.rorp.rorpdevlibs.preference

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.rorp.rorpdevlibs.constant.Constants
import org.jetbrains.annotations.NotNull

/*
     ____            __                              ____       _                  _
    |  _ \ _ __ ___ / _| ___ _ __ ___ _ __   ___ ___|  _ \  ___| | ___  __ _  __ _| |_ ___  _ __
    | |_) | '__/ _ \ |_ / _ \ '__/ _ \ '_ \ / __/ _ \ | | |/ _ \ |/ _ \/ _` |/ _` | __/ _ \| '__|
    |  __/| | |  __/  _|  __/ | |  __/ | | | (_|  __/ |_| |  __/ |  __/ (_| | (_| | || (_) | |
    |_|   |_|  \___|_|  \___|_|  \___|_| |_|\___\___|____/ \___|_|\___|\__, |\__,_|\__\___/|_|
                                                                       |___/
 */
/**
 * @author Matt Dev
 * @since 2021.02.08
 */
open class PreferenceDelegator(context: Context) {
    private val sharedPrefsFile     = Constants.SHARED_PREF
    private val masterKeyAlias      = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    private val sharedPreferences   = EncryptedSharedPreferences.create(context, sharedPrefsFile, masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

    fun put(@NotNull key: String, @NotNull value: String){
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun get(@NotNull key: String): String{
        return sharedPreferences.getString(key, "") ?: ""
    }

    fun remove(@NotNull key: String){
        sharedPreferences.edit().remove(key).apply()
    }

    fun removeAll(){
        sharedPreferences.edit().clear().apply()
    }
}