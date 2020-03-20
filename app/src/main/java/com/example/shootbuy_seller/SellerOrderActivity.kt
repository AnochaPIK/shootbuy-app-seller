package com.example.shootbuy_seller

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shootbuy_seller.Adapter.SellerOrderListAdapter
import com.example.shootbuy_seller.Models.ProductOrder.SellerOrder
import com.example.shootbuy_seller.Models.SellerOrderListData
import com.example.shootbuy_seller.Services.GetSellerOrderList

class SellerOrderActivity : AppCompatActivity() {
    private var sellOrderRecyclerView: RecyclerView? = null
    private var pref: SharedPreferences? = null
    private var sellerUuid: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_order)

        sellOrderRecyclerView = findViewById(R.id.sellerOrderRecyclerView)
        pref = getSharedPreferences("SP_Seller_DATA", Context.MODE_PRIVATE)
        sellerUuid = pref!!.getString("UUID", "")

        var url = IPAddress.ipAddress + "product-order/getSellerOrderList/" + sellerUuid
        GetSellerOrderList(listener).execute(url)
    }

    override fun onStart() {
        super.onStart()
        var url = IPAddress.ipAddress + "product-order/getSellerOrderList/" + sellerUuid
        GetSellerOrderList(listener).execute(url)
    }

    var listener = object : GetSellerOrderList.getDataComplete {
        override fun getDataComplete(sellerOrderList: List<SellerOrder>) {
            var sellerOrderListData = ArrayList<SellerOrderListData>()
            for (i in sellerOrderList.indices) {
                var address =
                    sellerOrderList[i].order!!.address!!.addressNumber + " " + sellerOrderList[i].order!!.address!!.district + " " +
                            sellerOrderList[i].order!!.address!!.subDistrict + " " + sellerOrderList[i].order!!.address!!.province + " " +
                            sellerOrderList[i].order!!.address!!.zipCode
                sellerOrderListData.add(
                    SellerOrderListData(
                        sellerOrderList[i].orderId,
                        sellerOrderList[i].assignDate,
                        address
                    )
                )
            }
            sellOrderRecyclerView!!.apply {
                layoutManager = LinearLayoutManager(this@SellerOrderActivity)
                adapter = SellerOrderListAdapter(sellerOrderListData, this@SellerOrderActivity)
            }
        }
    }
}
