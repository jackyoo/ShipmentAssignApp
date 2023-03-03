package com.jacky.shipment.utils

import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.jacky.shipment.domain.Destination
import com.jacky.shipment.domain.Driver

internal class UtilKtTest {

    @Test
    fun `need to return correct vowels count out of the string`() {
        assertThat("AEIOU".countVowels()).isEqualTo(5)
        assertThat(" aeiou".countVowels()).isEqualTo(5)
        assertThat("aeiOU ".countVowels()).isEqualTo(5)

        assertThat("rtv pp aeiOU".countVowels()).isEqualTo(5)
        assertThat("Everardo Welch".countVowels()).isEqualTo(5)
        assertThat("Orval Mayert ".countVowels()).isEqualTo(4)
    }

    @Test
    fun `need to return correct consonants count out of the string`() {
        assertThat("AEIOU".countConsonants()).isEqualTo(0)
        assertThat("eqroiuoiualcoie aeiou".countConsonants()).isEqualTo(4)

        assertThat("Everardo Welch".countConsonants()).isEqualTo(8)
        assertThat("Orval Mayert ".countConsonants()).isEqualTo(7)
    }

    @Test
    fun `need to return correct factors of the length of the string after trimming`() {
        assertThat("Orval Mayert ".factors()).isEqualTo(listOf(2, 3, 4, 6, 12))
        assertThat("Orval Mayert".factors()).isEqualTo(listOf(2, 3, 4, 6, 12))
        assertThat("Howard Emmerich".factors()).isEqualTo(listOf(3, 5, 15))
        assertThat("Howard Emmerichasfdsadfasfasdfasfsdf".factors()).isEqualTo(listOf(2, 3, 4, 6, 9, 12, 18, 36))
    }

    @Test
    fun `need to return whether it is odd size of the string after trimming`() {
        assertThat("123".isOdd()).isEqualTo(true)
        assertThat("123 ".isOdd()).isEqualTo(true)
        assertThat(" 123456 ".isOdd()).isEqualTo(false)
        assertThat("  ".isOdd()).isEqualTo(false)
    }

    @Test
    fun `need to return only street name out of the address`() {
        assertThat("8725 Aufderhar River Suite 859".onlyStreetName()).isEqualTo("Aufderhar River")
        assertThat("79035 Shanna Light Apt. 322".onlyStreetName()).isEqualTo("Shanna Light")
        assertThat("2431 Lindgren Corners".onlyStreetName()).isEqualTo("Lindgren Corners")
        assertThat("63187 Volkman Garden Suite 447".onlyStreetName()).isEqualTo("Volkman Garden")
    }

    @Test
    fun `match engine test`() {
        val driversName = listOf(
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
        )
        val destinationAddress = listOf(
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
        )

        val matchEngine = MatchDriverEngine(
            driversName.mapIndexed { index, driverName ->
                Driver(id = index, fullName = driverName, vowels = driverName.countVowels(), consonants = driverName.countConsonants(), factors = driverName.factors())
            },
            destinationAddress.mapIndexed { index, s ->
                Destination(id = index, streetNumberAndName = s, isOdd = s.onlyStreetName().isOdd(), factors = s.onlyStreetName().factors())
            }
        )
        matchEngine.matchDriver(0, 0)
        val map = matchEngine.getOptimalDriverMap()
        map.forEachIndexed {index, i ->
            println("$index $i")
        }
        println(matchEngine.optimalScore)
        assertThat(map[0]).isEqualTo(0)
        assertThat(map[1]).isEqualTo(1)
        assertThat(map[2]).isEqualTo(2)
        assertThat(map[3]).isEqualTo(4)
        assertThat(map[4]).isEqualTo(6)
        assertThat(map[5]).isEqualTo(3)
        assertThat(map[6]).isEqualTo(5)
        assertThat(map[7]).isEqualTo(7)
        assertThat(map[8]).isEqualTo(8)
        assertThat(map[9]).isEqualTo(9)
        assertThat(matchEngine.optimalScore).isEqualTo(97)
    }
}
