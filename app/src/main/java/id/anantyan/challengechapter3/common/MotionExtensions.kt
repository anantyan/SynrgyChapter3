package id.anantyan.challengechapter3.common

import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import com.androidpoet.metaphor.MetaphorActivity
import com.androidpoet.metaphor.MetaphorAnimation
import com.androidpoet.metaphor.MetaphorFragment
import com.androidpoet.metaphor.MetaphorView
import com.google.android.material.transition.MaterialArcMotion
import id.anantyan.challengechapter3.R

fun Fragment.doMaterialMotion(view: View, transitionName: String) {
    val metaphor = MetaphorFragment.Builder(this)
        .setExitDuration(resources.getInteger(R.integer.motion_duration_large).toLong())
        .setView(view)
        .setTransitionName(transitionName)
        .setExitAnimation(MetaphorAnimation.ContainerTransform)
        .setMotion(MaterialArcMotion())
        .build()
    metaphor.animate()
}

fun ComponentActivity.doMaterialMotion(view: View, transitionName: String) {
    val metaphor = MetaphorActivity.Builder(this)
        .setExitDuration(resources.getInteger(R.integer.motion_duration_small).toLong())
        .setView(view)
        .setTransitionName(transitionName)
        .setExitAnimation(MetaphorAnimation.ContainerTransform)
        .setMotion(com.google.android.material.transition.platform.MaterialArcMotion())
        .build()
    metaphor.animate()
}

fun View.doMaterialMotion(viewEnd: View) {
    val meta = MetaphorView.Builder(this)
        .setDuration(resources.getInteger(R.integer.motion_duration_large).toLong())
        .setEndView(viewEnd)
        .setMetaphorAnimation(MetaphorAnimation.SharedAxisYBackward)
        .setMotion(MaterialArcMotion())
        .build()
    meta.animate()
}