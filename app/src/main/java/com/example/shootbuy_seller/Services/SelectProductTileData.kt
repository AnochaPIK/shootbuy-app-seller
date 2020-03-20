package com.example.shootbuy_seller.Services

import android.os.AsyncTask
import com.example.shootbuy_seller.Models.ProductData.FoodAndBev
import com.example.shootbuy_seller.Models.ProductData.Tile
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL

class SelectProductTileData(var listener: getDataComplete) : AsyncTask<String, String, String>() {
    override fun doInBackground(vararg url: String?): String {


        var text: String
        val connection = URL(url[0]).openConnection() as HttpURLConnection
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
        var tileData =
            gson.fromJson(result.toString(), Array<Tile>::class.java).toList()
        listener.getDataComplete(tileData!!)
    }

    interface getDataComplete {
        fun getDataComplete(jsonString: List<Tile>)
    }
}