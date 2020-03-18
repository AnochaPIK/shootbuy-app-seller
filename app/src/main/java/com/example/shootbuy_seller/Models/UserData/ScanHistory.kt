package com.example.shootbuy_seller.Models.UserData

import com.example.shootbuy_seller.Models.ProductData.Product

class ScanHistory(
    val uuid: String? = null,
    val user: User? = null,
    val productId: String? = null,
    val product: Product? = null,
    val scanDateTime: String? = null
)