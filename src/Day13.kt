import kotlin.math.abs

fun main() {

    data class Button(val x: Long, val y: Long)
    data class Prize(val x: Long, val y: Long)
    data class Game(
        val a: Button,
        val b: Button,
        val prize: Prize,
        val aCost: Int = 3,
        val bCost: Int = 1,
    ) {
        fun findMinTokens(): Long? {
            return (0..prize.x / a.x).filter { i ->
                (prize.x - a.x * i) % b.x == 0L
            }.map { aTimes ->
                aTimes to (prize.x - a.x * aTimes) / b.x
            }.filter { (aTimes, bTimes) ->
                a.y * aTimes + b.y * bTimes == prize.y
            }.minOfOrNull { (aTimes, bTimes) ->
                aTimes * aCost + bTimes * bCost
            }
        }

        fun findMinTokensSmarter(): Long? {
            val dividend = abs(prize.y * b.x - prize.x * b.y)
            val divisor = abs(b.x * a.y - a.x * b.y)
            return if (dividend % divisor == 0L) {
                val aTimes = dividend / divisor
                val bTimes = (prize.x - a.x * aTimes) / b.x
                aTimes * aCost + bTimes * bCost
            } else {
                null
            }
        }
    }

    fun parse(input: List<String>): List<Game> {
        fun toButton(s: String): Button {
            val (xStr, yStr) = s.substringAfter(':').split(',')
            return Button(xStr.trim().removePrefix("X+").toLong(), yStr.trim().removePrefix("Y+").toLong())
        }

        fun toPrize(s: String): Prize {
            val (xStr, yStr) = s.substringAfter(':').split(',')
            return Prize(xStr.trim().removePrefix("X=").toLong(), yStr.trim().removePrefix("Y=").toLong())
        }

        val result = mutableListOf<Game>()

        var i = 0
        while (i < input.size) {
            result += Game(
                a = toButton(input[i]),
                b = toButton(input[i + 1]),
                prize = toPrize(input[i + 2]),
            )
            i += 4
        }

        return result
    }

    fun part1(input: List<String>): Long {
        return parse(input).mapNotNull { game ->
            game.findMinTokens()
        }.sum()
    }

    fun part2(input: List<String>): Long {
        return parse(input).map { game ->
            game.copy(
                prize = game.prize.copy(
                    x = game.prize.x + 10000000000000L,
                    y = game.prize.y + 10000000000000L,
                )
            )
        }.mapNotNull { game ->
            game.findMinTokensSmarter()
        }.sum()
    }

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
