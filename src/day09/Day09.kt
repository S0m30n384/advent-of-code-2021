package day09

import readInputToStringList

private const val PART1_CRITERIA = 15

private const val PART2_CRITERIA = 1134


/**
 * [Description](https://adventofcode.com/2021/day/9).
 */
fun main() {

    val testInput = toMatrix(readInputToStringList("day09/Day09_test"))

    val part1testInputResult = part1(testInput)
    check(part1testInputResult == PART1_CRITERIA) { "The result does not match the example in the description! Criteria: $PART1_CRITERIA. Result: $part1testInputResult." }

    val part2testInputResult = part2(testInput)
    check(part2testInputResult == PART2_CRITERIA) { "The result does not match the example in the description! Criteria: $PART2_CRITERIA. Result: $part2testInputResult." }

    val input = toMatrix(readInputToStringList("day09/Day09"))
    println(part1(input))
    println(part2(input))
}

fun toMatrix(input: List<String>) = input.map { it.toCharArray().map { char -> char.digitToInt() } }

fun part1(input: List<List<Int>>): Int {
    return findLowPoints(input).sumOf { input[it.first][it.second] + 1 }
}

fun findLowPoints(input: List<List<Int>>): List<Pair<Int, Int>> {
    val lowPointCoordinates = mutableListOf<Pair<Int, Int>>()
    input.forEachIndexed { listIndex, list ->
        list.forEachIndexed { index, number ->
            val surroundingElementList = mutableListOf<Int>()
            if (index > 0) {
                surroundingElementList.add(list[index - 1])
            }
            if (index < list.lastIndex) {
                surroundingElementList.add(list[index + 1])
            }
            if (listIndex > 0) {
                surroundingElementList.add(input[listIndex - 1][index])
            }
            if (listIndex < input.lastIndex) {
                surroundingElementList.add(input[listIndex + 1][index])
            }
            if (number < surroundingElementList.minOrNull()!!) {
                lowPointCoordinates.add(Pair(listIndex, index))
            }
        }
    }
    return lowPointCoordinates
}

fun part2(input: List<List<Int>>) = findLowPoints(input).map { searchNeighbours(input, it).size }.sortedDescending().take(3).reduce { acc, i -> acc * i }

fun searchNeighbours(input: List<List<Int>>, coordinates: Pair<Int, Int>): Set<Pair<Int, Int>> {
    val currentPoint = input[coordinates.first][coordinates.second]
    val neighbourList = mutableListOf<Pair<Int, Int>>()
    val alreadyFoundCoordinates = mutableSetOf(coordinates)

    if (coordinates.first > 0) {
        neighbourList.addNeighbourIfValid(coordinates.first - 1, coordinates.second, input, currentPoint)
    }

    if (coordinates.first < input.lastIndex) {
        neighbourList.addNeighbourIfValid(coordinates.first + 1, coordinates.second, input, currentPoint)
    }

    if (coordinates.second > 0) {
        neighbourList.addNeighbourIfValid(coordinates.first, coordinates.second - 1, input, currentPoint)
    }

    if (coordinates.second < input[0].lastIndex) {
        neighbourList.addNeighbourIfValid(coordinates.first, coordinates.second + 1, input, currentPoint)
    }

    alreadyFoundCoordinates.addAll(neighbourList)

    return alreadyFoundCoordinates + neighbourList.map { searchNeighbours(input, it) }.flatten().toSet()
}

fun MutableList<Pair<Int, Int>>.addNeighbourIfValid(x: Int, y: Int, input: List<List<Int>>, valueOfPoint: Int) {
    val neighbour = input[x][y]
    if (neighbour > valueOfPoint && neighbour != 9) {
        this.add(Pair(x, y))
    }
}
