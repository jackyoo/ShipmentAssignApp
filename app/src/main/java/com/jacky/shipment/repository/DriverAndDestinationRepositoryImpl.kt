package com.jacky.shipment.repository

import com.google.gson.Gson
import com.jacky.shipment.domain.Destination
import com.jacky.shipment.domain.Driver
import com.jacky.shipment.domain.DriverAndDestinations
import com.jacky.shipment.utils.*

data class RawJson(
    val shipments: List<String>,
    val drivers: List<String>
)

class DriverAndDestinationRepositoryImpl: DriverAndDestinationRepository {
    override suspend fun fetch(): DriverAndDestinations {
        val gson = Gson()

        val json = """
            {
              "shipments": [
                "215 Osinski Manors",
                "9856 Marvin Stravenue",
                "7127 Kathlyn Ferry",
                "987 Champlin Lake",
                "63187 Volkman Garden Suite 447",
                "75855 Dessie Lights",
                "1797 Adolf Island Apt. 744",
                "2431 Lindgren Corners",
                "8725 Aufderhar River Suite 859",
                "79035 Shanna Light Apt. 322"
              ],
              "drivers": [
                "Everardo Welch",
                "Orval Mayert",
                "Howard Emmerich",
                "Izaiah Lowe",
                "Monica Hermann",
                "Ellis Wisozk",
                "Noemie Murphy",
                "Cleve Durgan",
                "Murphy Mosciski",
                "Kaiser Sose"
              ]
            }
        """.trimIndent()

        val rawJson = gson.fromJson(json, RawJson::class.java)

        val drivers = rawJson.drivers.mapIndexed { index, it ->
            Driver(
                id = index,
                fullName = it,
                vowels = it.countVowels(),
                consonants = it.countConsonants(),
                factors = it.factors()
            )
        }
        val destinations = rawJson.shipments.mapIndexed { index, it ->
            Destination(
                id = index,
                streetNumberAndName = it,
                isOdd = it.onlyStreetName().isOdd(),
                factors = it.onlyStreetName().factors()
            )
        }

        return DriverAndDestinations(destinations = destinations, drivers = drivers)
    }
}
