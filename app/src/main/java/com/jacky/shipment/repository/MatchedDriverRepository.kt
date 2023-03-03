package com.jacky.shipment.repository

import com.jacky.shipment.domain.Destination

interface MatchedDriverRepository {
    suspend fun persistDriverMap(driverMap: Array<Int>, destinations: List<Destination>)

    suspend fun retrieveDriverMap(): List<Destination>
}