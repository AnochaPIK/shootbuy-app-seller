package com.example.shootbuy_seller.Models.UserData

import com.example.shootbuy_seller.Models.UserData.User

class Address(
    val addressId: Int? = null,
    val uuid: String? = null,
    val addressNumber: String? = null,
    val district: String? = null,
    val subDistrict: String? = null,
    val province: String? = null,
    val zipCode: String? = null,
    val user: User? = null
)