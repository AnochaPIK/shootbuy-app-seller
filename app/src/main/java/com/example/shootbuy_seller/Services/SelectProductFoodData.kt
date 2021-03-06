package com.example.shootbuy_seller.Services

import android.os.AsyncTask
import com.example.shootbuy_seller.Models.ProductData.FoodAndBev
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL

class SelectProductFoodData(var listener: getDataComplete) : AsyncTask<String, String, String>() {
    //    var listener:getDataComplete? = null
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
        var productFoodData =
            gson.fromJson(result.toString(), Array<FoodAndBev>::class.java).toList()
        listener.getDataComplete(productFoodData!!)
    }

    interface getDataComplete {
        fun getDataComplete(foodAndBevList: List<FoodAndBev>)
    }
}