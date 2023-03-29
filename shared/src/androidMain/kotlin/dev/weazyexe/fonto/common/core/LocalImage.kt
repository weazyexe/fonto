package dev.weazyexe.fonto.common.core

import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun LocalImage.asBitmap(): Bitmap {
   return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}