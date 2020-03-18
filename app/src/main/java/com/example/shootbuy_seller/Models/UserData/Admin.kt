package com.example.shootbuy_seller.Models.UserData

import com.example.shootbuy_seller.Models.ProductOrder.SellerOrder

class Admin(
    var adminUuid: String? = null,
    var adminEmail: String? = null,
    var adminFirstName: String? = null,
    var adminLastName: String? = null,
    var sellerOrder: List<SellerOrder>? = null
)