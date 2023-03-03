package com.jacky.shipment.repository

import com.jacky.shipment.domain.DriverAndDestinations

interface DriverAndDestinationRepository {
    suspend fun fetch(): DriverAndDestinations
}