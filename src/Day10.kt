fun main() {

    val destinations = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)

    fun findTrailHeads(heights: List<List<Int>>, x: Int, y: Int, height: Int): List<Pair<Int, Int>> {
        if (y !in 0 ..< heights.size) return listOf()
        if (x !in 0 ..< heights[y].size) return listOf()
        if (heights[y][x] != height) return listOf()
        if (height == 9) {
            return listOf(x to y)
        }

        return destinations.map { (xDiff, yDiff) ->
            findTrailHeads(heights, x + xDiff, y + yDiff, height + 1,)
        }.reduce { acc, next -> acc + next }
    }


    fun part1(input: List<String>): Int {
        val heights = input.map { string -> string.map { ch -> ch.digitToInt() } }

        return heights.mapIndexed { y, ints ->
            ints.mapIndexed { x, n ->
                if (n == 0) {
                    findTrailHeads(heights, x, y, 0).distinct().size
                } else {
                    0
                }
            }.sum()
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val heights = input.map { string -> string.map { ch -> ch.digitToInt() } }

        return heights.mapIndexed { y, ints ->
            ints.mapIndexed { x, n ->
                if (n == 0) {
                    findTrailHeads(heights, x, y, 0).size
                } else {
                    0
                }
            }.sum()
        }.sum()
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
