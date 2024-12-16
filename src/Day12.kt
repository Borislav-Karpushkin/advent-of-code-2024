fun main() {

    fun part1(input: List<String>): Int {
        var stones = input.first().split(" ").map { it.toInt() }
        repeat(5) {
            stones = stones.flatMap { n ->
                val nString = n.toString()
                when {
                    n == 0 -> listOf(1)
                    nString.length % 2 == 0 -> {
                        listOf(
                            nString.substring(0, nString.length / 2),
                            nString.substring(nString.length / 2)
                        ).map { it.toInt() }
                    }
                    else -> listOf(n * 2024)
                }
            }
        }
        println(stones)
        return stones.size
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
