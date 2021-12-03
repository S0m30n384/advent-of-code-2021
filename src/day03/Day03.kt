package day03

import readInputToStringList

private const val PART1_CRITERIA = 198

private const val PART2_CRITERIA = 230

fun createSumList(input: List<String>, stringMaxLength: Int): MutableList<Int> {
    val sumList = mutableListOf<Int>()

    for (i in 0 until stringMaxLength) {
        sumList.add(i, 0)
    }

    input.forEach {
        it.toCharArray().forEachIndexed { index, char ->
            run {
                sumList[index] += char.digitToInt()
            }
        }
    }
    return sumList
}

/**
 * [Description](https://adventofcode.com/2021/day/3).
 */
fun main() {

    val testInput = readInputToStringList("day03/Day03_test")

    val part1testInputResult = part1(testInput)
    check(part1testInputResult == PART1_CRITERIA) { "The result does not match the example in the description! Criteria: $PART1_CRITERIA. Result: $part1testInputResult." }

    val part2testInputResult = part2(testInput)
    check(part2testInputResult == PART2_CRITERIA) { "The result does not match the example in the description! Criteria: $PART2_CRITERIA. Result: $part2testInputResult." }

    val input = readInputToStringList("day03/Day03")
    println(part1(input))
    println(part2(input))
}

fun invertDataDimension(input: List<String>): MutableList<MutableList<Char>> {
    val data = mutableListOf<MutableList<Char>>()
    for (i in 0 until input.maxOf { it.length }) {
        data.add(mutableListOf())
    }
    input.forEach {
        it.toCharArray().forEachIndexed { index, c ->
            data[index].add(c)
        }
    }
    return data
}

fun part1(input: List<String>): Int {
    val mostCommonBitString = invertDataDimension(input).map {
        val partition = it.partition { c -> c == '1' }
        if (partition.first.size > partition.second.size) '1' else '0'
    }.joinToString("")

    val gammaRate = mostCommonBitString.toInt(2)
    val epsilonRate = inverse(mostCommonBitString).toInt(2)

    return gammaRate * epsilonRate
}

fun part2(input: List<String>): Int {
    val oxygenGeneratorRating = calculateOxygenGeneratorRating(input, 0)
    val co2ScrubberRating = calculateCO2ScrubberRating(input, 0)

    return oxygenGeneratorRating.toInt(2) * co2ScrubberRating.toInt(2)
}

fun calculateOxygenGeneratorRating(input: List<String>, i: Int): String {
    val currentCharToKeep = if (createSumList(input, input.maxOf { it.length })[i] >= (input.size / 2 + input.size % 2)) '1' else '0'
    val filter = input.filter { it[i] == currentCharToKeep }
    return if (filter.size > 1) {
        calculateOxygenGeneratorRating(filter, i + 1)
    } else filter.first()
}

fun calculateCO2ScrubberRating(input: List<String>, i: Int): String {
    val currentCharToKeep = if (createSumList(input, input.maxOf { it.length })[i] < (input.size / 2 + input.size % 2)) '1' else '0'
    val filter = input.filter { it[i] == currentCharToKeep }
    return if (filter.size > 1) {
        calculateCO2ScrubberRating(filter, i + 1)
    } else filter.first()
}

fun inverse(bitString: String) = bitString.replace("1", "x").replace("0", "1").replace("x", "0")

