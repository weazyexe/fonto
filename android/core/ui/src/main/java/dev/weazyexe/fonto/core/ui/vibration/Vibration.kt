package dev.weazyexe.fonto.core.ui.vibration

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator.VIBRATION_EFFECT_SUPPORT_YES
import android.os.VibratorManager
import androidx.annotation.RequiresApi


fun Context.vibrate() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        vibrateAndroid12OrHigher()
    } else {
        vibrateBeforeAndroid12()
    }
}

@RequiresApi(Build.VERSION_CODES.S)
private fun Context.vibrateAndroid12OrHigher() {
    val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
    val vibrator = vibratorManager.defaultVibrator
    val clickEffect = VibrationEffect.EFFECT_CLICK

    if (vibrator.areAllEffectsSupported(clickEffect) == VIBRATION_EFFECT_SUPPORT_YES) {
        vibrator.vibrate(VibrationEffect.createPredefined(clickEffect))
    }
}

private fun Context.vibrateBeforeAndroid12() {

}