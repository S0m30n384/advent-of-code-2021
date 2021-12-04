package day04

import readInputToStringList

private const val PART1_CRITERIA = 4512

private const val PART2_CRITERIA = 1924

data class BingoBoard(var boardWon: Boolean = false, val board: MutableList<MutableList<BingoCell>> = mutableListOf())

data class BingoCell(val number: Int, var isMarked: Boolean)

fun readBingoInput(readInputToStringList: List<String>): Pair<MutableList<BingoBoard>, List<Int>> {
    val bingoBoardList = mutableListOf<BingoBoard>()
    val chosenNumberList = readInputToStringList.first().split(",").map { it.toInt() }

    val skip = 6

    for (i in 0 until readInputToStringList.count { it == "" }) {
        bingoBoardList.add(i, BingoBoard())
        for (j in 0 until 5) {
            val i1 = (((i) * skip) + j) + 2
            val string = readInputToStringList[i1]
            bingoBoardList[i].board.add(mutableListOf())
            string.split("\\s".toRegex()).filter { it.contains("\\d".toRegex()) }.forEach {
                bingoBoardList[i].board[j].add(BingoCell(it.toInt(), false))
            }
        }
    }
    return Pair(bingoBoardList, chosenNumberList)
}

/**
 * [Description](https://adventofcode.com/2021/day/4).
 */
fun main() {

    val testInput = readBingoInput(readInputToStringList("day04/Day04_test"))

    val part1testInputResult = part1(testInput.first, testInput.second)
    check(part1testInputResult == PART1_CRITERIA) { "The result does not match the example in the description! Criteria: $PART1_CRITERIA. Result: $part1testInputResult." }

    val part2testInputResult = part2(testInput.first, testInput.second)
    check(part2testInputResult == PART2_CRITERIA) { "The result does not match the example in the description! Criteria: $PART2_CRITERIA. Result: $part2testInputResult." }

    val input = readBingoInput(readInputToStringList("day04/Day04"))
    println(part1(input.first, input.second))
    println(part2(input.first, input.second))
}

fun boardWon(bingoBoard: BingoBoard): Boolean {
    bingoBoard.board.forEach { mutableList ->
        if (mutableList.all { it.isMarked }) {
            bingoBoard.boardWon = true
            return true
        }
    }
    for (i in 0 until 5) {
        if (bingoBoard.board.all { it[i].isMarked }) {
            bingoBoard.boardWon = true
            return true
        }
    }
    return false
}

fun part1(input: MutableList<BingoBoard>, chosenNumberList: List<Int>): Int {
    chosenNumberList.forEach { chosenNumber ->
        input.forEach { bingoBoard ->
            bingoBoard.board.forEach { row ->
                row.firstOrNull { it.number == chosenNumber }?.isMarked = true
            }
            if (boardWon(bingoBoard)) {
                return bingoBoard.board.sumOf { row -> row.sumOf { if (!it.isMarked) it.number else 0 } } * chosenNumber
            }
        }
    }
    return 0
}

fun part2(input: MutableList<BingoBoard>, chosenNumberList: List<Int>): Int {
    chosenNumberList.forEach { chosenNumber ->
        input.forEach { bingoBoard ->
            bingoBoard.board.forEach { row ->
                row.firstOrNull { it.number == chosenNumber }?.isMarked = true
            }
            if (boardWon(bingoBoard) && input.none { !it.boardWon }) {
                return bingoBoard.board.sumOf { row -> row.sumOf { if (!it.isMarked) it.number else 0 } } * chosenNumber
            }
        }
    }
    return 0
}
