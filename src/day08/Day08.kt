package day08

import readInputToStringList

private const val PART1_CRITERIA = 26

private const val PART2_CRITERIA = 61229

data class Note(val patternList: List<String>, val outputList: List<String>)

val signalCountForNumbers = mapOf(0 to 6, 1 to 2, 2 to 5, 3 to 5, 4 to 4, 5 to 5, 6 to 6, 7 to 3, 8 to 7, 9 to 6)

/**
 * [Description](https://adventofcode.com/2021/day/8).
 */
fun main() {

    val testInput = convertToNoteList(readInputToStringList("day08/Day08_test"))

    val part1testInputResult = part1(testInput)
    check(part1testInputResult == PART1_CRITERIA) { "The result does not match the example in the description! Criteria: $PART1_CRITERIA. Result: $part1testInputResult." }

    val part2testInputResult = part2(testInput)
    check(part2testInputResult == PART2_CRITERIA) { "The result does not match the example in the description! Criteria: $PART2_CRITERIA. Result: $part2testInputResult." }

    val input = convertToNoteList(readInputToStringList("day08/Day08"))
    println(part1(input))
    println(part2(input))
}

fun convertToNoteList(stringList: List<String>) = stringList.map {
    val split = it.split("|")
    Note(split.first().split(" ").map { string -> string.trim() }.filter { string -> string.isNotBlank() },
        split.last().split(" ").map { string -> string.trim() }.filter { string -> string.isNotBlank() })
}.toList()

fun part1(noteList: List<Note>) = noteList.map { it.outputList }.flatten().count { isUniqueNumberOfSignals(it) }

fun isUniqueNumberOfSignals(string: String) =
    string.length == signalCountForNumbers[1] || string.length == signalCountForNumbers[4] || string.length == signalCountForNumbers[7] || string.length == signalCountForNumbers[8]

fun part2(noteList: List<Note>): Int {
    return noteList.sumOf { convertToInt(it) }
}

fun convertToInt(note: Note): Int {
    val decoderMap = decode((note.outputList + note.patternList).toMutableList())

    return note.outputList.map { segment ->
        decoderMap.filter {
            segment.toCharArray().sortedArray().contentEquals(it.value)
        }.map { it.key.toString() }
    }.flatten().joinToString("")
        .toInt()
}

fun decode(list: MutableList<String>): MutableMap<Int, CharArray> {
    val workingList = list.map { it.toCharArray().sortedArray() }.toMutableList()
    val wiringNumberMap = mutableMapOf<Int, CharArray>()

    decodeForNumber(1, wiringNumberMap, workingList) { charArray: CharArray -> charArray.size == 2 }
    decodeForNumber(4, wiringNumberMap, workingList) { charArray: CharArray -> charArray.size == 4 }
    decodeForNumber(7, wiringNumberMap, workingList) { charArray: CharArray -> charArray.size == 3 }
    decodeForNumber(8, wiringNumberMap, workingList) { charArray: CharArray -> charArray.size == 7 }

    decodeForNumber(9, wiringNumberMap, workingList) { charArray: CharArray ->
        charArray.size == 6 && charArray.intersect((wiringNumberMap[4]!! + wiringNumberMap[7]!!).sortedArray().toSet()).size == 5
    }

    decodeForNumber(3, wiringNumberMap, workingList) { charArray: CharArray ->
        charArray.size == 5 && charArray.toList().containsAll(wiringNumberMap[7]!!.toList())
    }

    decodeForNumber(2, wiringNumberMap, workingList) { charArray: CharArray ->
        charArray.size == 5 && charArray.intersect(wiringNumberMap[3]!!.toSet()).size == 4
                && charArray.intersect(wiringNumberMap[4]!!.toSet()).size == 2
    }

    decodeForNumber(5, wiringNumberMap, workingList) { charArray: CharArray ->
        charArray.size == 5
    }

    decodeForNumber(6, wiringNumberMap, workingList) { charArray: CharArray ->
        charArray.size == 6 && charArray.intersect(wiringNumberMap[5]!!.toSet()).size == 5
    }

    decodeForNumber(0, wiringNumberMap, workingList) { charArray: CharArray ->
        charArray.size == 6 && !charArray.contentEquals(wiringNumberMap[6]!!)
                && !charArray.contentEquals(wiringNumberMap[9]!!)
    }

    return wiringNumberMap
}

fun decodeForNumber(number: Int, wiringNumberMap: MutableMap<Int, CharArray>, list: MutableList<CharArray>, function: (CharArray) -> Boolean) {
    val numberCharArray = list.firstOrNull(function)
    if (numberCharArray != null) {
        wiringNumberMap[number] = numberCharArray
        list.removeIf { it.sortedArray().contentEquals(wiringNumberMap[number]!!.sortedArray()) }
    }
}
