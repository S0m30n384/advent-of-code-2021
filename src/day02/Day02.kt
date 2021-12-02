package day02

import day02.Command.*
import readInputToStringList

private val part1Position = Position(10, 15)

private val part1Criteria = part1Position.product()

private const val PART2_CRITERIA = 900

enum class Command {
    FORWARD, DOWN, UP
}

data class CommandStep(var command: Command, var value: Int)

data class Position(var horizontal: Int = 0, var depth: Int = 0, var aim: Int = 0) {
    fun product() = horizontal * depth
}

fun readInputToCommandStepList(name: String) = readInputToStringList(name).map {
    val split = it.split("\\s".toRegex())
    CommandStep(valueOf(split[0].uppercase()), split[1].toInt())
}

/**
 * [Description](https://adventofcode.com/2021/day/2).
 */
fun main() {

    val testInput = readInputToCommandStepList("day02/Day02_test")

    val part1testInputResult = part1(testInput)
    check(part1testInputResult == part1Criteria) { "The result does not match the example in the description! Criteria: $part1Criteria. Result: $part1testInputResult." }

    val part2testInputResult = part2(testInput)
    check(part2testInputResult == PART2_CRITERIA) { "The result does not match the example in the description! Criteria: $PART2_CRITERIA. Result: $part2testInputResult." }

    val input = readInputToCommandStepList("day02/Day02")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<CommandStep>): Int {
    val position = Position()
    input.forEach {
        when (it.command) {
            FORWARD -> position.horizontal += it.value
            DOWN -> position.depth += it.value
            UP -> position.depth -= it.value
        }
    }
    return position.product()
}

fun part2(input: List<CommandStep>): Int {
    val position = Position()
    input.forEach {
        when (it.command) {
            FORWARD -> {
                position.horizontal += it.value
                position.depth += position.aim * it.value
            }
            DOWN -> position.aim += it.value
            UP -> position.aim -= it.value
        }
    }
    return position.product()
}
