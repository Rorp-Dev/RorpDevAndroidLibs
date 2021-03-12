package com.rorp.rorpdevlibs.browser

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ShareBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val url = intent?.dataString
        if(!url.isNullOrBlank()){
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, url)
            val chooserIntent = Intent.createChooser(shareIntent, "ShareLink")
            chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            try {
                chooserIntent.setPackage("com.android.chrome")
                context?.startActivity(chooserIntent)
            } catch (e: Exception) {
                chooserIntent.setPackage(null)
                context?.startActivity(chooserIntent)
            }
        }
    }
}