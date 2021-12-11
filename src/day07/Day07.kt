package day07

import readInputLineToIntList
import kotlin.math.abs

private const val PART1_CRITERIA = 37

private const val PART2_CRITERIA = 168


/**
 * [Description](https://adventofcode.com/2021/day/7).
 */
fun main() {

    val testInput = readInputLineToIntList("day07/Day07_test")

    val part1testInputResult = calculateMinimumFuelConsumption(testInput)
    check(part1testInputResult == PART1_CRITERIA) { "The result does not match the example in the description! Criteria: $PART1_CRITERIA. Result: $part1testInputResult." }

    val part2testInputResult = calculateMinimumFuelConsumption(testInput, true)
    check(part2testInputResult == PART2_CRITERIA) { "The result does not match the example in the description! Criteria: $PART2_CRITERIA. Result: $part2testInputResult." }

    val input = readInputLineToIntList("day07/Day07")
    println(calculateMinimumFuelConsumption(input))
    println(calculateMinimumFuelConsumption(input, true))
}


fun calculateMinimumFuelConsumption(input: List<Int>, increasingConsumption: Boolean = false): Int {
    val min = input.minOf { it }
    val max = input.maxOf { it }

    val fuelMap: MutableMap<Int, Int> = mutableMapOf()

    if (increasingConsumption) {
        for (i in min..max) {
            fuelMap[i] = input.sumOf { 0.rangeTo(abs(i - it)).sum() }
        }
    } else {
        for (i in min..max) {
            fuelMap[i] = input.sumOf { abs(i - it) }
        }
    }
    return fuelMap.minOf { it.value }
}
