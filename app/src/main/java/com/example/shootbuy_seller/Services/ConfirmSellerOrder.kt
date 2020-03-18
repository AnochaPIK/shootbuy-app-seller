package com.example.shootbuy_seller.Services

import android.os.AsyncTask
import android.util.Log
import com.example.shootbuy_seller.Models.ProductOrder.SellerOrder
import com.example.shootbuy_seller.Models.UserData.Seller
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL

class ConfirmSellerOrder(var sellerOrder: SellerOrder) : AsyncTask<String, String, String>() {
    override fun doInBackground(vararg url: String?): String {


        var text: String
        val connection = URL(url[0]).openConnection() as HttpURLConnection
        try {
            var gson = Gson()
            var jsonString = gson.toJson(sellerOrder)
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json")
            var outputStream = connection.outputStream

            outputStream.write(jsonString.toByteArray())
            outputStream.flush()
            outputStream.close()
            text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
        } finally {
            connection.disconnect()
        }
        Log.d("responseCode", connection.responseCode.toString())
        return text
    }


}