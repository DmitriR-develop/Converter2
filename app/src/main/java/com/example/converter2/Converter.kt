package com.example.converter2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.converter2.model.IConverter
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileInputStream

class Converter(private val context: Context) : IConverter {

    override fun convertionImage(str: String) = Completable.fromAction {
        val image = context
            .contentResolver
            ?.openInputStream(Uri.parse(str))
            ?.buffered()
            ?.use { it.readBytes() }
        image.let {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it?.size!!)
            val file = File(
                context
                    .getExternalFilesDir(null), context.getString(R.string.conv_name)
            )
            FileInputStream(file).use { it ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
        }
    }
        .subscribeOn(Schedulers.io())
}

private fun Bitmap.compress(png: Bitmap.CompressFormat, i: Int, it: FileInputStream) {

}
