package day06

import readInputToString

private const val PART1_CRITERIA = 5934L

private const val PART2_CRITERIA = 26984457539

private const val PART1_ITERATION_COUNT = 80

private const val PART2_ITERATION_COUNT = 256

fun readInts(name: String) = readInputToString(name).split(",").map { it.trim().toInt() }.toMutableList()

/**
 * [Description](https://adventofcode.com/2021/day/6).
 */
fun main() {

    val testInput = readInts("day06/Day06_test")

    val part1testInputResult = countFish(testInput, PART1_ITERATION_COUNT)
    check(part1testInputResult == PART1_CRITERIA) { "The result does not match the example in the description! Criteria: $PART1_CRITERIA. Result: $part1testInputResult." }

    val part2testInputResult = countFish(testInput, PART2_ITERATION_COUNT)
    check(part2testInputResult == PART2_CRITERIA) { "The result does not match the example in the description! Criteria: $PART2_CRITERIA. Result: $part2testInputResult." }

    val input = readInts("day06/Day06")
    println(countFish(input, PART1_ITERATION_COUNT))
    println(countFish(input, PART2_ITERATION_COUNT))
}

fun countFish(input: MutableList<Int>, iterationCount: Int): Long {
    var fishGenerationMap = input.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()

    for (i in 0..8) {
        fishGenerationMap.putIfAbsent(i, 0)
    }
    repeat(iterationCount) {
        fishGenerationMap = dayIteration(fishGenerationMap)
    }
    return fishGenerationMap.map { it.value }.sum()
}

fun dayIteration(eachCount: MutableMap<Int, Long>): MutableMap<Int, Long> {
    val endOfDayMap = mutableMapOf<Int, Long>()

    eachCount.toSortedMap().onEach {
        when (it.key) {
            0 -> {
                endOfDayMap[6] = it.value
                endOfDayMap[8] = it.value
            }
            7 -> {
                endOfDayMap[it.key - 1] = it.value + endOfDayMap[it.key - 1]!!
            }
            else -> {
                endOfDayMap[it.key - 1] = it.value
            }
        }
    }
    return endOfDayMap
}



