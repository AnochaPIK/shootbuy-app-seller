package com.example.shootbuy_seller.Services

import android.os.AsyncTask
import android.os.FileUtils
import android.util.Log
import com.google.gson.Gson
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class Multipost(var file: File) : AsyncTask<String, String, String>() {
    private val LINE_FEED = "\r\n"
    private val maxBufferSize = 1024 * 1024
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
//            var writer: PrintWriter =
//                PrintWriter(OutputStreamWriter(outputStream, charset("UTF-8")), true)
            var writer = DataOutputStream(outputStream)

            writer.writeBytes("--")
            writer.writeBytes(boundary)
            writer.writeBytes(LINE_FEED)
            writer.writeBytes("Content-Disposition: form-data; name=\"")
            writer.writeBytes("image")
            writer.writeBytes("\" filename=\"")
            writer.writeBytes(file.name)
            writer.writeBytes("\"")
            writer.writeBytes(LINE_FEED)
            writer.writeBytes("Content-Type: ")
            writer.writeBytes("text/plain; charset=utf-8")
            writer.writeBytes(LINE_FEED)
            writer.writeBytes(LINE_FEED)
            writer.flush()

            val inputStream = FileInputStream(file)
            inputStream.copyTo(outputStream, maxBufferSize)

            outputStream.flush()
            inputStream.close()
            writer.writeBytes(LINE_FEED)
            writer.flush()




            text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
        } finally {
            connection.disconnect()
        }
        Log.d("responseCode", connection.responseCode.toString())
        return text
    }
}