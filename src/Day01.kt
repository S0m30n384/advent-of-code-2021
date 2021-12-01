private const val PART1_CRITERIA = 7

private const val PART2_CRITERIA = 5

private const val PART2_SLIDING_WINDOW_SIZE = 3

fun main() {

    val testInput = readInput("Day01_test").map { it.toInt() }

    val part1testInputResult = part1(testInput)
    println(part1testInputResult)
    check(part1testInputResult == PART1_CRITERIA) { "The result does not match the example in the description!" }

    val part2testInputResult = part2(testInput)
    println(part2testInputResult)
    check(part2testInputResult == PART2_CRITERIA) { "The result does not match the example in the description!" }

    val input = readInput("Day01").map { it.toInt() }
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
