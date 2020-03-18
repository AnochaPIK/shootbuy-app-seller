package com.example.shootbuy_seller.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shootbuy_seller.Models.SellerOrderItemData
import com.example.shootbuy_seller.R
import com.squareup.picasso.Picasso
import java.text.NumberFormat

class SellerOrderDetailAdapter(val sellerOrderItemDataList: ArrayList<SellerOrderItemData>) :
    RecyclerView.Adapter<SellerOrderDetailAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val sellerOrderDetailItemImage =
            itemView.findViewById(R.id.seller_order_detail_item_image) as ImageView
        val sellerOrderDetailItemBrand =
            itemView.findViewById(R.id.seller_order_detail_item_brand) as TextView
        val sellerOrderDetailItemModel =
            itemView.findViewById(R.id.seller_order_detail_item_model) as TextView
        val sellerOrderDetailItemPrice =
            itemView.findViewById(R.id.seller_order_detail_item_price) as TextView
        val sellerOrderDetailItemTotalPrice =
            itemView.findViewById(R.id.seller_order_detail_item_total_price) as TextView
        val sellerOrderDetailItemQuantity =
            itemView.findViewById(R.id.seller_order_detail_item_quantity) as TextView


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.seller_order_detail_item, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return sellerOrderItemDataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(sellerOrderItemDataList[position].imageResource)
            .into(holder.sellerOrderDetailItemImage)
        holder.sellerOrderDetailItemBrand.text = sellerOrderItemDataList[position].brand
        holder.sellerOrderDetailItemModel.text = sellerOrderItemDataList[position].model
        holder.sellerOrderDetailItemPrice.text =
            "฿" + NumberFormat.getInstance().format(sellerOrderItemDataList[position].price).toString()
        holder.sellerOrderDetailItemTotalPrice.text =
            "Total ฿" + NumberFormat.getInstance().format((sellerOrderItemDataList[position].price!! * sellerOrderItemDataList[position].amount!!)).toString()
        holder.sellerOrderDetailItemQuantity.text =
            sellerOrderItemDataList[position].amount.toString()
    }
}