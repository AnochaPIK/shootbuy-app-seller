package com.example.shootbuy_seller.Models.ProductData

import com.google.firebase.ml.md.kotlin.EntityModels.ProductData.Color


class Electronic(
    val electronicId: String? = null,
    val electronicBrand: String? = null,
    val electronicModel: String? = null,
    val electronicImage: String? = null,
    val electronicPrice: Double? = null,
    val electronicSpec: String? = null,
    val electronicAmount: Int? = null,
    val color: List<Color>? = null
)