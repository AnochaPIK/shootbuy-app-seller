package com.example.shootbuy_seller

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shootbuy_seller.Adapter.SellerOrderDetailAdapter
import com.example.shootbuy_seller.Models.ProductData.FoodAndBev
import com.example.shootbuy_seller.Models.ProductData.Tile
import com.example.shootbuy_seller.Models.ProductOrder.Order
import com.example.shootbuy_seller.Models.SellerOrderItemData
import com.example.shootbuy_seller.Services.GetProductOrderByOrderId
import com.example.shootbuy_seller.Services.SelectProductFoodData
import com.example.shootbuy_seller.Services.SelectProductTileData

class SellerOrderDetailActivity : AppCompatActivity(), View.OnClickListener {


    private var sellOrderDetailRecyclerView: RecyclerView? = null
    private var signatureBtn: RelativeLayout? = null
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = MenuInflater(this).inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> {
                val intent = Intent(this, MainActivity::class.java)
//            Log.d("Address",oldHolder!!.address.text.toString())
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("Logout", "Logout")

                startActivity(intent)
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
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
                } else if (categoryId == 4) {
                    val urlSelectData =
                        IPAddress.ipAddress + "product-data/selectProductTileData/${productId}"

                    val listener = object : SelectProductTileData.getDataComplete {
                        override fun getDataComplete(tileList: List<Tile>) {

                            val tile = tileList[0]
                            sellerOrderItemDataList.add(
                                SellerOrderItemData(
                                    orderDetail[i].orderId,
                                    tile.tileBrand,
                                    tile.tileModel,
                                    orderDetail[i].quantity,
                                    tile.tilePrice,
                                    tile.tilePrice!! * orderDetail[i].quantity!!,
                                    tile.tileImage
                                )
                            )
                            sellOrderDetailRecyclerView!!.apply {
                                layoutManager = LinearLayoutManager(this@SellerOrderDetailActivity)
                                adapter = SellerOrderDetailAdapter(sellerOrderItemDataList)
                            }
                        }

                    }
                    SelectProductTileData(listener).execute(urlSelectData)
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
        var intent = Intent(this, SignatureActivity::class.java)
        intent.putExtra("orderId", orderId)
        startActivity(intent)

    }


}
