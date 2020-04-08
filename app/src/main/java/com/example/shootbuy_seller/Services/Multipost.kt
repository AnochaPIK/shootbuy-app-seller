package com.example.shootbuy_seller.Services

import android.os.AsyncTask
import android.os.FileUtils
import android.util.Log
import com.google.gson.Gson
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import android.R.attr.bitmap
import android.graphics.Bitmap
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth


class Multipost(var bitmap: Bitmap, var file: File,var orderId:Int?) : AsyncTask<String, String, String>() {
    private val crlf = "\r\n"
    private val twoHyphens = "--"
    var boundary = "*****" + (System.currentTimeMillis()) + "*****"
    override fun doInBackground(vararg params: String?): String {
        var text: String
        val connection = URL(params[0]).openConnection() as HttpURLConnection
        connection.doOutput = true
        connection.setRequestProperty(
            "Content-Type",
            "multipart/form-data; boundary=" + boundary
        )
        try {

            var outputStream = connection.outputStream
            var writer = DataOutputStream(outputStream)

            writer.writeBytes(this.twoHyphens + this.boundary + this.crlf)
            writer.writeBytes("Content-Disposition: form-data; name=\"" + "orderId" + "\"" + this.crlf)
            writer.writeBytes("Content-Type: text/plain; charset=UTF-8" + this.crlf)
            writer.writeBytes(this.crlf)
            writer.writeBytes(orderId.toString() + this.crlf)
            writer.flush()

            writer.writeBytes(twoHyphens + boundary + crlf)
            writer.writeBytes("Content-Disposition: form-data; name=\"" + "image" + "\";filename=\"" + file.name + "\"" + crlf)
            writer.writeBytes(crlf)

            var bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)

            writer.write(bos.toByteArray())
            writer.writeBytes(crlf)
            writer.writeBytes(twoHyphens + boundary + twoHyphens + crlf)
            writer.flush()
            writer.close()




            text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
        } finally {
            connection.disconnect()
        }
        Log.d("responseCode", connection.responseCode.toString())
        return text
    }
}