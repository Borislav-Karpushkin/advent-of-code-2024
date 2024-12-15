import java.math.BigInteger
import java.util.LinkedList

fun main() {

    // basically, two pointers solution
    fun part1(input: List<String>): BigInteger {
        val ints = input.first().map(Char::digitToInt)
        var sum = BigInteger.ZERO
        var li = 0
        var ri = if (ints.size % 2 == 0) ints.size - 2 else ints.size - 1
        var rRemaining = ints[ri]

        var i = 0
        while (li < ri) {
            if (li % 2 == 0) {
                repeat(ints[li]) {
                    sum += (li / 2 * i).toBigInteger()
                    ++i
                }
            } else {
                repeat(ints[li]) {
                    while (rRemaining == 0) {
                        ri -= 2
                        rRemaining = ints[ri]
                    }
                    if (ri > li) {
                        sum += (ri / 2 * i).toBigInteger()
                        ++i
                        --rRemaining
                    }
                }
            }
            ++li
        }

        if (ri == li) {
            repeat(rRemaining) {
                sum += (ri / 2 * i).toBigInteger()
                ++i
            }
        }

        return sum
    }

    // stupid solution
    fun part2(input: List<String>): BigInteger {
        val ints = input.first().map(Char::digitToInt).toMutableList()

        val data = ints.mapIndexed { i, n ->
            if (i % 2 == 0) {
                i / 2 to n
            } else {
                null to n
            }
        }.toMutableList()

        var ri = if (ints.size % 2 == 0) ints.size - 2 else ints.size - 1
        while (ri > 0) {
            val (n, rightCount) = data[ri]
            if (n == null) {
                --ri
                continue
            }

            for (j in 0 ..< ri) {
                val (leftN, leftCount) = data[j]
                if (leftN == null && leftCount >= rightCount) {
                    data[j] = data[ri]
                    data[ri] = null to rightCount
                    if (leftCount > rightCount) {
                        data.add(j + 1, null to (leftCount - rightCount))
                        ++ri
                    }
                    break
                }
            }

            --ri
        }

        var sum = BigInteger.ZERO
        var iSum = 0
        data.forEach { (n, count) ->
            repeat(count) {
                sum += ((n ?: 0) * iSum).toBigInteger()
                ++iSum
            }
        }

        return sum
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
