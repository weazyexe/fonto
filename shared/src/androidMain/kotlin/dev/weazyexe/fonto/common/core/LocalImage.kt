package dev.weazyexe.fonto.common.core

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import dev.weazyexe.fonto.common.model.base.LocalImage
import java.io.ByteArrayOutputStream

fun LocalImage.asBitmap(): Bitmap? {
   return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

fun Bitmap.asLocalImage(): LocalImage {
   val outputStream = ByteArrayOutputStream()
   compress(Bitmap.CompressFormat.PNG, 100, outputStream)
   return LocalImage(outputStream.toByteArray())
}