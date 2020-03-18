package com.example.shootbuy_seller.Models.UserData

import com.example.shootbuy_seller.Models.ProductOrder.Order

class User(
    var uuid: String? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var address: List<Address>? = null,
    var scanHistory: List<ScanHistory>? = null,
    var order: List<Order>? = null
)