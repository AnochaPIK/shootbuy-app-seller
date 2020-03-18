package com.example.shootbuy_seller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shootbuy_seller.Adapter.SellerOrderDetailAdapter
import com.example.shootbuy_seller.Models.ProductData.FoodAndBev
import com.example.shootbuy_seller.Models.ProductOrder.Order
import com.example.shootbuy_seller.Models.ProductOrder.SellerOrder
import com.example.shootbuy_seller.Models.SellerOrderItemData
import com.example.shootbuy_seller.Services.ConfirmSellerOrder
import com.example.shootbuy_seller.Services.GetProductOrderByOrderId
import com.example.shootbuy_seller.Services.GetSellerOrderList
import com.example.shootbuy_seller.Services.SelectProductFoodData

class SellerOrderDetailActivity : AppCompatActivity(), View.OnClickListener {


    private var sellOrderDetailRecyclerView: RecyclerView? = null
    private var signatureBtn: Button? = null
    private var orderId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_order_detail)
        orderId = intent.getIntExtra("orderId", 0)

        sellOrderDetailRecyclerView = findViewById(R.id.sellerOrderDetailRecyclerView)
        signatureBtn = findViewById(R.id.signatureBtn)
        signatureBtn!!.setOnClickListener(this)
        var url = IPAddress.ipAddress + "product-order/getProductOrderByOrderId/" + orderId
        GetProductOrderByOrderId(listener).execute(url)
    }

    val listener = object : GetProductOrderByOrderId.getDataComplete {
        override fun getDataComplete(orderList: List<Order>) {
            val order = orderList[0]

            val orderDetail = order.orderDetail
            var sellerOrderItemDataList = ArrayList<SellerOrderItemData>()

            for (i in orderDetail!!.indices) {
                val categoryId = orderDetail[i].product!!.categoryId
                val productId = orderDetail[i].product!!.productId
                if (categoryId == 1) {
                    val urlSelectData =
                        IPAddress.ipAddress + "product-data/selectProductFoodData/${productId}"

                    val listenerSelectData = object : SelectProductFoodData.getDataComplete {
                        override fun getDataComplete(foodAndBevList: List<FoodAndBev>) {

                            val foodAndBev = foodAndBevList[0]
                            sellerOrderItemDataList.add(
                                SellerOrderItemData(
                                    orderDetail[i].orderId,
                                    foodAndBev.foodAndBevBrand,
                                    foodAndBev.foodAndBevModel,
                                    orderDetail[i].quantity,
                                    foodAndBev.foodAndBevPrice,
                                    foodAndBev.foodAndBevPrice!! * orderDetail[i].quantity!!,
                                    foodAndBev.foodAndBevImage
                                )
                            )
                            sellOrderDetailRecyclerView!!.apply {
                                layoutManager = LinearLayoutManager(this@SellerOrderDetailActivity)
                                adapter = SellerOrderDetailAdapter(sellerOrderItemDataList)
                            }
                        }

                    }
                    SelectProductFoodData(listenerSelectData).execute(urlSelectData)
                } else if (categoryId == 2) {
                } else if (categoryId == 3) {
                }
            }

        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.signatureBtn -> {
                signatureFunction()
            }

        }
    }

    private fun signatureFunction() {
        var sellerOrderData = SellerOrder(null, orderId)
        val url =
            IPAddress.ipAddress + "product-order/confirmSellerOrder"
        ConfirmSellerOrder(sellerOrderData).execute(url)
        finish()
    }


}
