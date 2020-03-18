package com.example.shootbuy_seller.Services

import android.os.AsyncTask
import com.example.shootbuy_seller.Models.ProductOrder.SellerOrder
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL

class GetSellerOrderList(var listener: getDataComplete) : AsyncTask<String, String, String>() {
    override fun doInBackground(vararg params: String?): String {

        var text: String
        val connection = URL(params[0]).openConnection() as HttpURLConnection
        connection.connect()
        try {
            text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
        } finally {
            connection.disconnect()
        }

        return text
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        var gson = Gson()
        var sellerOrderList = gson.fromJson(result, Array<SellerOrder>::class.java).toList()
        listener.getDataComplete((sellerOrderList))
    }

    interface getDataComplete {
        fun getDataComplete(jsonString: List<SellerOrder>)
    }
}