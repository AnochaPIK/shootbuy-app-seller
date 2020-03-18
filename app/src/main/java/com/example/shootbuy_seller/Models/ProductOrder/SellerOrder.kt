package com.example.shootbuy_seller.Models.ProductOrder

import com.example.shootbuy_seller.Models.ProductOrder.Order
import com.example.shootbuy_seller.Models.UserData.Admin
import com.example.shootbuy_seller.Models.UserData.Seller

class SellerOrder(
    var sellerUuid: String? = null,
    var orderId: Int? = null,
    var assignBy: String? = null,
    var assignDate: String? = null,
    var finishDate: String? = null,
    var sellerOrderStatus: Int? = null,
    var sellerOrderSignatureImage: String? = null,
    var seller: Seller? = null,
    var order: Order? = null,
    var admin: Admin? = null
)