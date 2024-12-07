import java.math.BigInteger
import kotlin.math.pow
import kotlin.math.roundToInt

fun main() {

    fun countOfRightEquations(result: Long, numbers: List<Long>): Int {
        return (0 ..< 2.0.pow(numbers.size - 1).toInt()).count { repetition ->
            numbers.foldIndexed(0L) { i, acc, n ->
                when {
                    i == 0 -> acc + n
                    (repetition / 2.0.pow(i - 1).toInt()) % 2 == 0 -> acc + n
                    else ->  acc * n
                }
            } == result
        }
    }

    fun countRightEquationsWithConcatenation(result: Long, numbers: List<Long>): Int {
        return (0 ..< 3.0.pow(numbers.size - 1).toInt()).count { repetition ->
            numbers.foldIndexed(0L) { i, acc, n ->
                val operation = if (i > 0) (repetition / 3.0.pow(i - 1).toInt()) % 3 else 0
                when (operation) {
                    0 -> acc + n
                    1 -> acc * n
                    else -> "$acc$n".toLong()
                }
            } == result
        }
    }

    fun part1(input: List<String>): Long {
        return input.map { string ->
            val (result, equation) = string.split(":".toRegex())
            result.toLong() to equation.trim().split(" ").map { it.toLong() }
        }.sumOf { (result, numbers) ->
            result.takeIf { countOfRightEquations(result, numbers) > 0 } ?: 0
        }
    }

    fun part2(input: List<String>): Long {
        return input.map { string ->
            val (result, equation) = string.split(":".toRegex())
            result.toLong() to equation.trim().split(" ").map { it.toLong() }
        }.sumOf { (result, numbers) ->
            result.takeIf { countRightEquationsWithConcatenation(result, numbers) > 0 } ?: 0
        }
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
