import java.math.BigInteger

fun main() {

    fun transform(n: Long): List<Long> {
        val nString = n.toString()
        return when {
            n == 0L -> listOf(1L)
            nString.length % 2 == 0 -> {
                listOf(
                    nString.substring(0, nString.length / 2),
                    nString.substring(nString.length / 2)
                ).map { it.toLong() }
            }
            else -> listOf(n * 2024)
        }
    }

    fun countStonesSimple(input: List<Long>, blinks: Int): Int {
        var stones = input

        repeat(blinks) { i ->
            stones = stones.flatMap { n ->
                transform(n)
            }
        }

        return stones.size
    }

    fun countStonesSmarter(input: List<Long>, blinks: Int): BigInteger {
        var stones = input.associateWith { lng -> BigInteger.ONE }

        repeat(blinks) { i ->
            val newStones = mutableMapOf<Long, BigInteger>()
            stones.map { (n, count) ->
                transform(n).forEach { transformed ->
                    val countTransformed = newStones.getOrDefault(transformed, BigInteger.ZERO)
                    newStones[transformed] = countTransformed + count
                }
            }
            stones = newStones
        }

        return stones.map { entry -> entry.value }.reduce { acc, integer -> acc + integer }
    }

    fun part1(input: List<String>): Int {
        return countStonesSimple(input.first().split(" ").map { it.toLong() }, 25)
    }

    fun part2(input: List<String>): BigInteger {
        return countStonesSmarter(input.first().split(" ").map { it.toLong() }, 75)
    }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
