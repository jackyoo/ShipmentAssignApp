package com.jacky.shipment.domain

data class Destination(
    val id: Int,
    val streetNumberAndName: String,
    val isOdd: Boolean,
    val factors: List<Int>
)