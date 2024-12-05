fun main() {

    fun parseInput(input: List<String>): Pair<MutableMap<Int, MutableSet<Int>>, MutableList<List<Int>>> {
        val increasingRules = mutableMapOf<Int, MutableSet<Int>>()
        val pageLines = mutableListOf<List<Int>>()

        input.forEach { line ->
            if (line.contains("|")) {
                val (lowerPage, upperPage) = line.split("|").map(String::toInt)
                increasingRules.getOrPut(lowerPage) { mutableSetOf() }.add(upperPage)
            } else if (line.isNotBlank()) {
                pageLines += line.split(",").map(String::toInt)
            }
        }

        return Pair(increasingRules, pageLines)
    }

    fun isRightOrdered(
        pages: List<Int>,
        increasingRules: MutableMap<Int, MutableSet<Int>>
    ): Boolean {
        return pages.zipWithNext().none { (a, b) -> increasingRules[b]?.contains(a) == true }
    }

    fun fixOrder(pages: List<Int>, increasingRules: MutableMap<Int, MutableSet<Int>>): List<Int> {
        val result = pages.toMutableList()

        do {
            for (i in 0 ..< pages.lastIndex) {
                if (increasingRules[result[i + 1]]?.contains(result[i]) == true) {
                    val a = result[i]
                    result[i] = result[i + 1]
                    result[i + 1] = a
                }
            }
        } while (!isRightOrdered(result, increasingRules))

        return result
    }

    fun part1(input: List<String>): Int {
        val (increasingRules, pageLines) = parseInput(input)

        return pageLines.filter { pages ->
            isRightOrdered(pages, increasingRules)
        }.sumOf { pages ->
            pages[pages.size / 2]
        }
    }

    fun part2(input: List<String>): Int {
        val (increasingRules, pageLines) = parseInput(input)

        return pageLines
            .filterNot { pages -> isRightOrdered(pages, increasingRules) }
            .map { pages ->
                fixOrder(pages, increasingRules)
            }.sumOf { pages ->
                pages[pages.size / 2]
            }
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
