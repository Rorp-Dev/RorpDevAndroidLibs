package com.rorp.rorpdevlibs.browser

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.rorp.rorpdevlibs.R

class Browser {
    companion object{
        fun openUrl(context: Context, url: String){
            val share = Intent(context, ShareBroadcastReceiver::class.java)
            share.action = Intent.ACTION_SEND

            val copy = PendingIntent.getBroadcast(context, 0, Intent(context, CustomTabsCopyReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
            val builder = CustomTabsIntent.Builder()
            builder.addMenuItem("CopyLink", copy)

            builder.setShowTitle(true)
            builder.setActionButton(
                BitmapFactory.decodeResource(context.resources, R.drawable.ic_share),
                "ShareFile",
                PendingIntent.getBroadcast(context, 0, share, 0),
                true
            )

            val intent: CustomTabsIntent = builder.build()
            intent.intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            try {
                intent.intent.setPackage("com.android.chrome")
                intent.launchUrl(context, Uri.parse(url))
            } catch (e: Exception) {
                intent.intent.setPackage(null)
                intent.launchUrl(context, Uri.parse(url))
            }
        }
    }
}