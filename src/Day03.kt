fun main() {
    fun getMulSum(line: String): Long {
        val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")
        return regex.findAll(line).map { it.value }.sumOf { pair ->
            val (a, b) = pair.removePrefix("mul(").removeSuffix(")").split(",").map { it.toLong() }
            a * b
        }
    }

    fun getMulSumWithSwitches(line: String): Long {
        val regex = Regex("(mul\\(\\d{1,3},\\d{1,3}\\)|don't\\(\\)|do\\(\\))")
        var closed = false
        return regex.findAll(line).toList().map { it.value  }.sumOf { str ->
            when {
                str == "don't()" -> {
                    closed = true
                    0L
                }
                str == "do()" -> {
                    closed = false
                    0L
                }
                str.startsWith("mul") && closed == false -> {
                    val (a, b) = str.removePrefix("mul(").removeSuffix(")").split(",").map { it.toLong() }
                    a * b
                }
                else -> 0L
            }
        }
    }

    fun String.replaceDontMuls(): String {
        var line = this
        val start = "don't()"
        val end = "do()"

        while (line.contains(start)) {
            val startIndex = line.indexOf(start)
            val endIndex = line.indexOf(end, startIndex).takeIf { it != -1 } ?: line.length
            line = line.removeRange(startIndex, endIndex)
        }

        return line
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            getMulSum(line)
        }
    }

    fun part2(input: List<String>): Long {
        return getMulSumWithSwitches(input.fold("") { acc, v -> acc + v })
        /*return input.sumOf {
            getMulSumWithSwitches(it)
        }*/
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
