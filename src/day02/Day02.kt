package day02

import readInputToStringList

private val part1Position = Position(10, 15)

private val part1Criteria = part1Position.product()

data class CommandStep(var command: String, var value: Int)

data class Position(var horizontal: Int, var depth: Int) {
    fun product() = horizontal * depth
}

fun readInputToCommandStepList(name: String) = readInputToStringList(name).map {
    val split = it.split("\\s".toRegex())
    CommandStep(split[0], split[1].toInt())
}

/**
 * [Description](https://adventofcode.com/2021/day/2).
 */
fun main() {

    val testInput = readInputToCommandStepList("day02/Day02_test")

    val part1testInputResult = part1(testInput)
    check(part1testInputResult == part1Criteria) { "The result does not match the example in the description! Criteria: $part1Criteria. Result: $part1testInputResult." }

//    val part2testInputResult = part2(testInput)
//    check(part2testInputResult == PART2_CRITERIA) { "The result does not match the example in the description! Criteria: $PART2_CRITERIA. Result: $part2testInputResult." }

    val input = readInputToCommandStepList("day02/Day02")
    println(part1(input))
//    println(part2(input))
}

fun part1(input: List<CommandStep>): Int {
    val position = Position(0, 0)
    input.forEach {
        when (it.command) {
            "forward" -> position.horizontal = position.horizontal + it.value
            "down" -> position.depth = position.depth + it.value
            "up" -> position.depth = position.depth - it.value
        }
    }
    return position.product()
}
