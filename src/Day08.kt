fun main() {

    fun parseAntennasCoordinates(input: List<String>): Map<Char, List<Pair<Int, Int>>> {
        return input.flatMapIndexed { y, string ->
            string.mapIndexed { x, ch -> ch to (x to y) }
                .filter { it.first != '.' }
        }.groupBy(keySelector = { pair -> pair.first }, valueTransform = { pair -> pair.second })
    }

    fun part1(input: List<String>): Int {
        val yMax = input.size - 1
        val xMax = input.first().length - 1
        val antinodes = mutableSetOf<Pair<Int, Int>>()
        parseAntennasCoordinates(input).forEach { charToCoordinates ->
            val coordinates = charToCoordinates.value
            for (i in 0..<coordinates.size - 1) {
                for (j in i + 1..<coordinates.size) {
                    val a = coordinates[i]
                    val b = coordinates[j]
                    val diff = (b.first - a.first) to (b.second - a.second)
                    antinodes.add((b.first + diff.first) to (b.second + diff.second))
                    antinodes.add((a.first - diff.first) to (a.second - diff.second))
                }
            }
        }
        return antinodes.filter { (x, y) -> x in 0..xMax && y in 0..yMax }.size
    }

    fun part2(input: List<String>): Int {
        val yMax = input.size - 1
        val xMax = input.first().length - 1
        val antinodes = mutableSetOf<Pair<Int, Int>>()
        val antennasCoordinates = parseAntennasCoordinates(input)
        antennasCoordinates.forEach { charToCoordinates ->
            val coordinates = charToCoordinates.value
            for (i in 0..<coordinates.size - 1) {
                for (j in i + 1..<coordinates.size) {
                    var a = coordinates[i]
                    var b = coordinates[j]
                    val diff = (b.first - a.first) to (b.second - a.second)
                    while (b.first + diff.first in 0..xMax && b.second + diff.second in 0..yMax) {
                        b = (b.first + diff.first) to (b.second + diff.second)
                        antinodes.add(b)
                    }

                    while (a.first - diff.first in 0..xMax && a.second - diff.second in 0..yMax) {
                        a = (a.first - diff.first) to (a.second - diff.second)
                        antinodes.add(a)
                    }
                }
            }
        }
        return (antinodes + antennasCoordinates.flatMap { it.value }).distinct().size
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
