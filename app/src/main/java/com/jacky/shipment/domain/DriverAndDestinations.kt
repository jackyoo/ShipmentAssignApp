package com.jacky.shipment.domain

data class DriverAndDestinations(
    val destinations: List<Destination>,
    val drivers: List<Driver>
)
