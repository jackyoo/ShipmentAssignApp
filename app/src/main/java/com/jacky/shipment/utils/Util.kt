package com.jacky.shipment.utils

import com.jacky.shipment.domain.Destination
import com.jacky.shipment.domain.Driver

fun String.countVowels(): Int {
    val vowels = setOf('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U')
    var count = 0
    for (c in this) {
        if (c in vowels) count ++
    }
    return count
}

fun String.countConsonants(): Int {
    val vowels = setOf('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U')
    var count = 0
    for (c in this) {
        if (c !in vowels && c.isLetter()) count++
    }
    return count
}

fun String.factors(): List<Int> {
    val factors = mutableListOf<Int>()
    val length = this.trim().length
    for (i in 2..length) {
        if (length % i == 0) {
            factors.add(i)
        }
    }
    return factors
}

fun String.isOdd(): Boolean {
    return (this.trim().length % 2 != 0)
}

fun String.onlyStreetName(): String {
    val parts = this.split(" ")
    return parts[1] + " " + parts[2]
}

class MatchDriverEngine(
    private val drivers: List<Driver>,
    private val destinations: List<Destination>
) {
    var optimalScore = 0
    private var optimalDrivermap = Array(drivers.size) { -1 }
    private var driverMap = Array(drivers.size) { -1 }
    private var isDestTaken = Array(destinations.size) { false }
    private var scoreCache = Array(drivers.size) {
        Array(destinations.size) {0}
    }

    fun matchDriver(driverIdx: Int, currentScore: Int) {
        if (driverIdx == drivers.size) { // if all drivers has chosen, it's time to find the highest score.
            if (optimalScore < currentScore) {
                optimalScore = currentScore
                optimalDrivermap = driverMap.copyOf()
            }
            return
        }
        for (i in driverIdx until drivers.size) {
            for (j in destinations.indices) {
                if (!isDestTaken[j]) {
                    val score = getScore(drivers[i], destinations[j])
                    driverMap[i] = j
                    isDestTaken[j] = true
                    matchDriver(i + 1, currentScore + score)
                    driverMap[i] = -1
                    isDestTaken[j] = false
                }
            }
        }
    }

    fun getOptimalDriverMap(): Array<Int> {
        return optimalDrivermap
    }

    private fun getScore(driver: Driver, destination: Destination): Int {
        if (scoreCache[driver.id][destination.id] != 0) {
            return scoreCache[driver.id][destination.id]
        }
        var score: Double = when (destination.isOdd) {
            true -> driver.consonants.toDouble() // odd
            false -> driver.vowels * 1.5 // even
        }
        if (isSharedFactors(driver.factors, destination.factors)) {
            score *= 1.5
        }
        scoreCache[driver.id][destination.id] = score.toInt()
        return score.toInt()
    }

    private fun isSharedFactors(factorsA: List<Int>, factorsB: List<Int>): Boolean  {
        val setB = factorsB.toSet()
        factorsA.forEach {
            if (it in setB) return true
        }
        return false
    }
}
