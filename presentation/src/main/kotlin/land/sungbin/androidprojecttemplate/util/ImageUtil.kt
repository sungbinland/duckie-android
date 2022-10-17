package land.sungbin.androidprojecttemplate.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

object ImageUtil {

    fun saveGalleryImage(context: Context, image: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            image,
            "ImageFromDuckie",
            null
        )
        return Uri.parse(path)
    }
}