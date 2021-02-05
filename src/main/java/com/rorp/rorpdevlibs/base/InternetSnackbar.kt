package com.rorp.rorpdevlibs.base

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.rorp.rorpdevlibs.R
import java.lang.Exception

/*
     ___       _                       _   ____                   _    _
    |_ _|_ __ | |_ ___ _ __ _ __   ___| |_/ ___| _ __   __ _  ___| | _| |__   __ _ _ __
     | || '_ \| __/ _ \ '__| '_ \ / _ \ __\___ \| '_ \ / _` |/ __| |/ / '_ \ / _` | '__|
     | || | | | ||  __/ |  | | | |  __/ |_ ___) | | | | (_| | (__|   <| |_) | (_| | |
    |___|_| |_|\__\___|_|  |_| |_|\___|\__|____/|_| |_|\__,_|\___|_|\_\_.__/ \__,_|_|

 */
/**
 * @author Matt Dev
 * @since 2021.02.05
 */
open class InternetSnackbar {
    companion object{
        const val OFFLINE : Int = 0
        const val ONLINE : Int = 1

        fun internet(context: Context, view: View, message: String, status: Int){

           try {
               // setDuration -> OFFLINE : show forever, ONLINE : show by duration
               val snackbar: Snackbar = Snackbar.make(view, "", if(status == OFFLINE) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG)
               val layout: Snackbar.SnackbarLayout = snackbar.view as Snackbar.SnackbarLayout

               layout.setBackgroundColor(ContextCompat.getColor(context, if(status == OFFLINE) R.color.color_ff4b5c else R.color.color_28df99))

               val defaultText: TextView = layout.findViewById(R.id.snackbar_text) as TextView
               val lp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
               lp.setMargins(0,0,0,0)
               lp.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15F, context.resources.displayMetrics).toInt()
               defaultText.layoutParams = lp

               // inflate custom SnackBar layout
               val snackView: View = View.inflate(context, R.layout.snacbbar_layout, null)
               snackView.layoutParams = lp

               // set internet status to TextView
               val tvStatus: TextView = snackView.findViewById(R.id.tv_internet_status) as TextView
               tvStatus.text = message
               tvStatus.setTextColor(ContextCompat.getColor(context, if(status == OFFLINE) R.color.color_ffffff else R.color.color_000000))

               // set internet status background color
               val lnStatus: LinearLayout = snackView.findViewById(R.id.ll_internet_status)
               lnStatus.setBackgroundColor(ContextCompat.getColor(context, if(status == OFFLINE) R.color.color_ff4b5c else R.color.color_28df99))

               layout.addView(snackView, 0)
               snackbar.show()
               if(status == ONLINE) {
                   if (snackbar.isShown){
                       snackbar.dismiss()
                   }
               }
           }catch (e: Exception){
               e.printStackTrace()
           }
        }
    }
}
