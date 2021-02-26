package com.rorp.rorpdevlibs.util.extensions

import android.os.Handler
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rorp.rorpdevlibs.R

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

enum class TransitionAnimation {
    FADE, SLIDE_UP, SLIDE_IN, NO_ANIMATION
}

fun FragmentTransaction.setCustomAnimation(type: TransitionAnimation) {
    when (type) {
        TransitionAnimation.SLIDE_IN -> setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
        TransitionAnimation.SLIDE_UP -> setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom, R.anim.enter_from_bottom, R.anim.exit_to_bottom)
        TransitionAnimation.FADE -> setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        TransitionAnimation.NO_ANIMATION -> return
    }
}

inline fun <reified T : Fragment> newFragmentInstance(vararg params: Pair<String, Any>): T =
    T::class.java.newInstance().apply {
        arguments = bundleOf(*params)
    }

fun DialogFragment.showWithAnimation(container: View, fragment: Fragment, backstackTag: String) {
    Handler().post {
        val transaction = childFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
        transaction.replace(container.id, fragment)
        transaction.addToBackStack(backstackTag)
        transaction.commitAllowingStateLoss()
    }

}