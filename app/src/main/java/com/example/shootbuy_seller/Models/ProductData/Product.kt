package com.example.shootbuy_seller.Models.ProductData

import com.example.shootbuy_seller.Models.ProductData.Category
import com.example.shootbuy_seller.Models.UserData.ScanHistory

class Product(
    val productId: String? = null, val categoryId: Int? = null, val category: Category? = null,
    val scanHistory: List<ScanHistory>? = null
)