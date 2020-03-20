package com.example.shootbuy_seller

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.shootbuy_seller.Adapter.SellerOrderListAdapter
import com.example.shootbuy_seller.Models.ProductOrder.SellerOrder
import com.example.shootbuy_seller.Models.SellerOrderListData
import com.example.shootbuy_seller.Services.GetSellerOrderList

class SellerOrderActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {


    private var sellOrderRecyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var pref: SharedPreferences? = null
    private var sellerUuid: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_order)
        sellOrderRecyclerView = findViewById(R.id.sellerOrderRecyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout!!.setOnRefreshListener(this)
        pref = getSharedPreferences("SP_Seller_DATA", Context.MODE_PRIVATE)
        sellerUuid = pref!!.getString("UUID", "")

        setDataList()
    }

    private fun setDataList() {
        swipeRefreshLayout!!.isRefreshing = true
        var url = IPAddress.ipAddress + "product-order/getSellerOrderList/" + sellerUuid
        GetSellerOrderList(listener).execute(url)
    }

    override fun onRefresh() {
        setDataList()
    }

    override fun onStart() {
        super.onStart()
        var url = IPAddress.ipAddress + "product-order/getSellerOrderList/" + sellerUuid
        GetSellerOrderList(listener).execute(url)
    }

    var listener = object : GetSellerOrderList.getDataComplete {
        override fun getDataComplete(sellerOrderList: List<SellerOrder>) {
            swipeRefreshLayout!!.isRefreshing = false

            var sellerOrderListData = ArrayList<SellerOrderListData>()
            for (i in sellerOrderList.indices) {
                sellerOrderListData.add(
                    SellerOrderListData(
                        sellerOrderList[i].orderId,
                        sellerOrderList[i].assignDate,
                        sellerOrderList[i].order!!.address!!.addressNumber,
                        sellerOrderList[i].order!!.address!!.district,
                        sellerOrderList[i].order!!.address!!.subDistrict,
                        sellerOrderList[i].order!!.address!!.province,
                        sellerOrderList[i].order!!.address!!.zipCode
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
