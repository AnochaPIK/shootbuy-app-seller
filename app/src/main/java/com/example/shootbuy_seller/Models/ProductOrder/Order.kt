package com.example.shootbuy_seller.Models.ProductOrder

import com.example.shootbuy_seller.Models.UserData.Address
import com.example.shootbuy_seller.Models.UserData.User

class Order(
    var orderId: Int? = null,
    var uuid: String? = null,
    var addressId: Int? = null,
    var user: User? = null,
    var orderDateTime: String? = null,
    var totalPrice: Int? = null,
    var orderStatus: Int? = null,
    var orderDetail: List<OrderDetail>? = null,
    var sellerOrder: SellerOrder? = null,

    var address: Address? = null

)