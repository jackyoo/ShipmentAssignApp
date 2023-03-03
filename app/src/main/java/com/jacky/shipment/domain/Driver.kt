package com.jacky.shipment.domain

data class Driver(
    val id: Int, // There could be multiple drivers with the same name
    val fullName: String,
    val vowels: Int,
    val consonants: Int,
    val factors: List<Int>
)