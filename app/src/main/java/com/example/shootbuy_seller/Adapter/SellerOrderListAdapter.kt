package com.example.shootbuy_seller.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.shootbuy_seller.Models.SellerOrderListData
import com.example.shootbuy_seller.R
import com.example.shootbuy_seller.SellerOrderActivity
import com.example.shootbuy_seller.SellerOrderDetailActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SellerOrderListAdapter(
    var sellerOrderListData: ArrayList<SellerOrderListData>? = null,
    var context: SellerOrderActivity? = null
) :
    RecyclerView.Adapter<SellerOrderListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderId = itemView.findViewById<TextView>(R.id.orderId)
        val assignDate = itemView.findViewById<TextView>(R.id.assignDate)
        val address = itemView.findViewById<TextView>(R.id.address)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.seller_order_list_item, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return sellerOrderListData!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var dateTime = sellerOrderListData!![position].assignDate.toString()
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
        parser.timeZone = TimeZone.getTimeZone("THA")
        val formattedDate =
            formatter.format(parser.parse(sellerOrderListData!![position].assignDate.toString())!!)

        holder.orderId.text = sellerOrderListData!![position].orderId.toString()
        holder.assignDate.text = formattedDate
        holder.address.text = sellerOrderListData!![position].address.toString()

        holder.itemView.setOnClickListener {
            var intent = Intent(context, SellerOrderDetailActivity::class.java)
            intent.putExtra("orderId", sellerOrderListData!![position].orderId)
            startActivity(context!!, intent, null)
//            context!!.finish()

        }

    }
}
