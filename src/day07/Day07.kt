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

    val part1testInputResult = part1(testInput)
    check(part1testInputResult == PART1_CRITERIA) { "The result does not match the example in the description! Criteria: $PART1_CRITERIA. Result: $part1testInputResult." }

    val part2testInputResult = part2(testInput)
    check(part2testInputResult == PART2_CRITERIA) { "The result does not match the example in the description! Criteria: $PART2_CRITERIA. Result: $part2testInputResult." }

    val input = readInputLineToIntList("day07/Day07")
    println(part1(input))
    println(part2(input))
}


fun part1(input: List<Int>): Int {
    val min = input.minOf { it }
    val max = input.maxOf { it }

    val fuelMap: MutableMap<Int, Int> = mutableMapOf()

    for (i in min..max) {
        fuelMap[i] = input.sumOf { abs(i - it) }
    }

    return fuelMap.minOf { it.value }
}

fun part2(input: List<Int>): Int {
    val min = input.minOf { it }
    val max = input.maxOf { it }

    val fuelMap: MutableMap<Int, Int> = mutableMapOf()

    for (i in min..max) {
        fuelMap[i] = input.sumOf { 0.rangeTo(abs(i - it)).sum() }
    }

    return fuelMap.minOf { it.value }
}
