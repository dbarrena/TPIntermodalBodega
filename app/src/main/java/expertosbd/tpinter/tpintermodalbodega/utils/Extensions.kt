package expertosbd.tpinter.tpintermodalbodega.utils

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.design.widget.Snackbar

fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
    this.view.setBackgroundColor(colorInt)
    return this
}