package com.example.shootbuy_seller.Models.ProductData

import com.google.firebase.ml.md.kotlin.EntityModels.ProductData.Color

class Furniture(
    val furnitureId: String? = null,
    val furnitureBrand: String? = null,
    val furnitureModel: String? = null,
    val furnitureImage: String? = null,
    val furniturePrice: Int? = null,
    val furnitureSize: String? = null,
    val furnitureDetail: String? = null,
    val furnitureAmount: Int? = null,
    val color: List<Color>? = null
)

