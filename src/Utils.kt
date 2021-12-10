import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInputToStringList(name: String) = File("src", "$name.txt").readLines()

fun readInputToString(name: String) = File("src", "$name.txt").readText()

/**
 * Reads Int lines from the given input txt file.
 */
fun readInputToIntList(name: String) = readInputToStringList(name).map { it.toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
