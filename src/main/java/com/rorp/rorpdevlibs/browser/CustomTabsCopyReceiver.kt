package com.rorp.rorpdevlibs.browser

import android.content.*
import android.widget.Toast

class CustomTabsCopyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val url = intent?.dataString
        if(!url.isNullOrBlank()){
            addToClipboard(context, url)
            Toast.makeText(context, "LinkCopied", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Copy to clipboard
     */
    private fun addToClipboard(context: Context?, str: String){
        try {
            val clipboard: ClipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip      = ClipData.newPlainText("label", str)
            clipboard.setPrimaryClip(clip)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}