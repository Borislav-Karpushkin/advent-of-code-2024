import java.util.LinkedList

fun main() {

    fun List<String>.turnRight(): List<String> {
        val strings = this
        return first().mapIndexed { index, ch ->
            strings.map { string ->
                string[index]
            }.reversed().joinToString("")
        }
    }

    fun String.countOfXmas(): Int {
        var count = 0
        var indexOfXmas = indexOf("XMAS", 0)
        while (indexOfXmas != -1) {
            ++count
            indexOfXmas = indexOf("XMAS", indexOfXmas + 1)
        }
        return count
    }

    fun findXmasInRows(input: List<String>): Int {
        return input.sumOf { s ->
            s.countOfXmas()
        }
    }

    /**
     * 00 01 02 03 04 05
     * 10 11 12 13 14 15
     * 20 21 22 23 24 25
     * 30 31 32 33 34 35
     * 40 41 42 43 44 45
     * 50 51 52 53 54 55
     */
    fun findXmasInDiagonals(input: List<String>): Int {
        var sum = 0
        val size = input.size
        val sLen = input.first().length

        // 00 11 22 33 44 55
        // 10 21 32 43 54
        // 20 32 43 54
        for (i in 0 until size - 3) {
            var s = LinkedList<Char>()
            for (j in 0 until size - i) {
                s += input[i + j][j]
            }
            sum += s.joinToString("").countOfXmas()
        }

        // 01 12 23 34 45
        // 02 13 24 35
        for (i in 1 until sLen - 3) {
            var s = LinkedList<Char>()
            for (j in 0 until sLen - i) {
                s += input[j][i + j]
            }
            sum += s.joinToString("").countOfXmas()
        }

        return sum
    }

    /**
     * MMMSXXMASM
     * MSAMXMSMSA
     * AMXSXMAAMM
     * MSAMASMSMX
     * XMASAMXAMM
     * XXAMMXXAMA
     * SMSMSASXSS
     * SAXAMASAAA
     * MAMMMXMMMM
     * MXMXAXMASX
     */
    fun part1(input: List<String>): Long {
        var sum = 0L
        var strings = input
        repeat(4) { i ->
            sum += findXmasInRows(strings)
            sum += findXmasInDiagonals(strings)
            if (i < 3) strings = strings.turnRight()
        }
        return sum
    }

    fun findMas(input: List<String>): Int {
        var sum = 0
        for (i in 0 until input.first().length - 2) {
            for (j in 0 until input.size - 2) {
                if (
                    input[i][j] == 'M' &&
                    input[i][j + 2] == 'S' &&
                    input[i + 1][j + 1] == 'A' &&
                    input[i + 2][j] == 'M' &&
                    input[i + 2][j + 2] == 'S'
                ) {
                    sum += 1
                }
            }
        }
        return sum
    }

    /**
     * .M.S......
     * ..A..MSMS.
     * .M.S.MAA..
     * ..A.ASMSM.
     * .M.S.M....
     * ..........
     * S.S.S.S.S.
     * .A.A.A.A..
     * M.M.M.M.M.
     * ..........
     */
    fun part2(input: List<String>): Long {
        var sum = 0L
        var strings = input
        repeat(4) { i ->
            sum += findMas(strings)
            if (i < 3) strings = strings.turnRight()
        }
        return sum
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
