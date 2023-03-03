package com.jacky.shipment.repository

import com.jacky.shipment.domain.Destination

class MatchedDriverRepositoryImpl: MatchedDriverRepository {
    private var matchedDriverMap = listOf<Destination>()

    override suspend fun persistDriverMap(driverMap: Array<Int>, destinations: List<Destination>) {
        matchedDriverMap = driverMap.map { destIdx ->
            destinations[destIdx]
        }
    }

    override suspend fun retrieveDriverMap(): List<Destination> {
        return matchedDriverMap
    }
}
