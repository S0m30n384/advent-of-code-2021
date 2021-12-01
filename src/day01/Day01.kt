package day01

import readInputToIntList

private const val PART1_CRITERIA = 7

private const val PART2_CRITERIA = 5

private const val PART2_SLIDING_WINDOW_SIZE = 3

/**
 * [Description](https://adventofcode.com/2021/day/1).
 */
fun main() {

    val testInput = readInputToIntList("day01/Day01_test")

    val part1testInputResult = part1(testInput)
    check(part1testInputResult == PART1_CRITERIA) { "The result does not match the example in the description! Criteria: $PART1_CRITERIA. Result: $part1testInputResult." }

    val part2testInputResult = part2(testInput)
    check(part2testInputResult == PART2_CRITERIA) { "The result does not match the example in the description! Criteria: $PART2_CRITERIA. Result: $part2testInputResult." }

    val input = readInputToIntList("day01/Day01")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<Int>): Int {
    var depthIncreaseCounter = 0
    var previousMeasurement = input.first()

    for (index in 1..input.lastIndex) {
        val currentMeasurement = input[index]
        if (currentMeasurement > previousMeasurement) depthIncreaseCounter++
        previousMeasurement = currentMeasurement
    }

    return depthIncreaseCounter
}

fun part2(input: List<Int>): Int {
    var depthIncreaseCounter = 0
    var previousMeasurement = measureDepth(input, 0, PART2_SLIDING_WINDOW_SIZE)

    for (i in 1..input.size - PART2_SLIDING_WINDOW_SIZE) {
        val currentMeasurement = measureDepth(input, i, i + PART2_SLIDING_WINDOW_SIZE)
        if (currentMeasurement > previousMeasurement) depthIncreaseCounter++
        previousMeasurement = currentMeasurement
    }

    return depthIncreaseCounter
}

fun measureDepth(input: List<Int>, startIndex: Int, endIndex: Int) = input.subList(startIndex, endIndex).sum()
