package day05

import readInputToStringList

private const val PART1_CRITERIA = 5

private const val PART2_CRITERIA = 12

data class Line(val beginning: Pair<Int, Int>, val end: Pair<Int, Int>)

fun readLineInput(readInputToStringList: List<String>): List<Line> {
    return readInputToStringList.map { line ->
        val coordinateSplit = line.split("->").map {
            val split = it.split(",")
            Pair(split[0].trim().toInt(), split[1].trim().toInt())
        }
        Line(coordinateSplit[0], coordinateSplit[1])
    }
}

/**
 * [Description](https://adventofcode.com/2021/day/5).
 */
fun main() {

    val testInput = readLineInput(readInputToStringList("day05/Day05_test"))

    val part1testInputResult = part1(testInput)
    check(part1testInputResult == PART1_CRITERIA) { "The result does not match the example in the description! Criteria: $PART1_CRITERIA. Result: $part1testInputResult." }

    val part2testInputResult = part2(testInput)
    check(part2testInputResult == PART2_CRITERIA) { "The result does not match the example in the description! Criteria: $PART2_CRITERIA. Result: $part2testInputResult." }

    val input = readLineInput(readInputToStringList("day05/Day05"))
    println(part1(input))
    println(part2(input))
}

fun initializeDiagram(size: Int): MutableList<MutableList<Int>> {
    val diagram = mutableListOf<MutableList<Int>>()
    for (i in 0..size) {
        val list = mutableListOf<Int>()
        for (j in 0..size) {
            list.add(0)
        }
        diagram.add(list)
    }
    return diagram
}

fun part1(input: List<Line>): Int {
    val diagram = initializeDiagram(
        maxOf(input.maxOf { it.beginning.first },
            input.maxOf { it.beginning.second },
            input.maxOf { it.end.first },
            input.maxOf { it.end.second })
    )
    input.forEach {
        if (it.beginning.first == it.end.first) {
            val intProgression = if (it.beginning.second > it.end.second) it.beginning.second.downTo(it.end.second) else it.beginning.second.rangeTo(it.end.second)
            for (i in intProgression) {
                diagram[i][it.beginning.first]++
            }
        } else if (it.beginning.second == it.end.second) {
            val intProgression = if (it.beginning.first > it.end.first) it.beginning.first.downTo(it.end.first) else it.beginning.first.rangeTo(it.end.first)
            for (i in intProgression) {
                diagram[it.beginning.second][i]++
            }
        }
    }

    return diagram.sumOf { x -> x.count { it >= 2 } }
}


fun part2(input: List<Line>): Int {
    val diagram = initializeDiagram(
        maxOf(input.maxOf { it.beginning.first },
            input.maxOf { it.beginning.second },
            input.maxOf { it.end.first },
            input.maxOf { it.end.second })
    )
    input.forEach {
        if (it.beginning.first == it.end.first) {
            val intProgression = if (it.beginning.second > it.end.second) it.beginning.second.downTo(it.end.second) else it.beginning.second.rangeTo(it.end.second)
            for (i in intProgression) {
                diagram[i][it.beginning.first]++
            }
        } else if (it.beginning.second == it.end.second) {
            val intProgression = if (it.beginning.first > it.end.first) it.beginning.first.downTo(it.end.first) else it.beginning.first.rangeTo(it.end.first)
            for (i in intProgression) {
                diagram[it.beginning.second][i]++
            }
        } else {
            val xCoordinates = (if (it.beginning.second > it.end.second) it.beginning.second.downTo(it.end.second) else it.beginning.second.rangeTo(it.end.second)).toMutableList()
            val yCoordinates = (if (it.beginning.first > it.end.first) it.beginning.first.downTo(it.end.first) else it.beginning.first.rangeTo(it.end.first)).toMutableList()

            for (i in 0..xCoordinates.lastIndex) {
                diagram[xCoordinates[i]][yCoordinates[i]]++
            }
        }
    }

    return diagram.sumOf { x -> x.count { it >= 2 } }
}
