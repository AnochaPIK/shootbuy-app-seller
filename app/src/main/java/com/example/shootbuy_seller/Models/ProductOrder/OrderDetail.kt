package com.example.shootbuy_seller.Models.ProductOrder

import com.example.shootbuy_seller.Models.ProductData.Product

class OrderDetail(
    val orderId: Int? = null,
    val order: Order? = null,
    val productId: String? = null,
    val product: Product? = null,
    val quantity: Int? = null
)