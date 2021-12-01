private const val PART1_CRITERIA = 7

private const val PART2_CRITERIA = 5

private const val PART2_SLIDING_WINDOW_SIZE = 3

fun main() {

    val testInput = readInput("Day01_test")

    val part1testInputResult = part1(testInput)
    println(part1testInputResult)
    check(part1testInputResult == PART1_CRITERIA) { "The result does not match the example in the description!" }

    val part2testInputResult = part2(testInput)
    println(part2testInputResult)
    check(part2testInputResult == PART2_CRITERIA) { "The result does not match the example in the description!" }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    val intList = input.map { it.toInt() }
    var depthIncreaseCounter = 0
    var previousMeasurement = intList.first()

    for (index in 1..intList.lastIndex) {
        val currentMeasurement = intList[index]
        if (currentMeasurement > previousMeasurement) depthIncreaseCounter++
        previousMeasurement = currentMeasurement
    }

    return depthIncreaseCounter
}

fun part2(input: List<String>): Int {
    val intList = input.map { it.toInt() }
    var depthIncreaseCounter = 0
    var previousMeasurement = intList.subList(0, PART2_SLIDING_WINDOW_SIZE).sum()

    for (i in 1..intList.size - PART2_SLIDING_WINDOW_SIZE) {
        val currentMeasurement = intList.subList(i, i + PART2_SLIDING_WINDOW_SIZE).sum()
        if (currentMeasurement > previousMeasurement) depthIncreaseCounter++
        previousMeasurement = currentMeasurement
    }

    return depthIncreaseCounter
}
