package com.example.shootbuy_seller.Models.UserData

import com.example.shootbuy_seller.Models.ProductOrder.SellerOrder

class Seller(
    var sellerUuid: String? = null,
    var sellerEmail: String? = null,
    var sellerFirstName: String? = null,
    var sellerLastName: String? = null,
    var sellerOrder: List<SellerOrder>? = null
)