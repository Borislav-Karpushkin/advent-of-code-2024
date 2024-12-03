fun main() {
    fun isProperInc(a: Int, b: Int): Boolean {
        val diff = b - a
        return diff > 0 && diff < 4
    }

    fun findLastDirectIncrementingIndex(list: List<Int>, start: Int = 0): Int {
        var i = start
        while (i < list.size - 1 && isProperInc(list[i], list[i + 1])) {
            ++i
        }
        return i
    }

    fun isIncrementing(list: List<Int>): Boolean {
        return findLastDirectIncrementingIndex(list, 0) == list.size - 1
    }

    fun isIncrementingListWithMistake(list: List<Int>): Boolean {
        val size = list.size
        val lastIncrementingIndex = findLastDirectIncrementingIndex(list, 0)
        if (lastIncrementingIndex >= size - 2) {
            return true
        }

        val newList = list.toMutableList()
        newList.removeAt(lastIncrementingIndex + 1)
        if (findLastDirectIncrementingIndex(newList, 0) == size - 2) {
            return true
        }

        val newList2 = list.toMutableList()
        newList2.removeAt(lastIncrementingIndex)
        return findLastDirectIncrementingIndex(newList2, 0) == size - 2
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { str ->
            str.split(" ")
                .map { it.toInt() }
                .let { ints ->
                    if (isIncrementing(ints) || isIncrementing(ints.reversed())) {
                        1L
                    } else {
                        0L
                    }
                }
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { str ->
            str.split(" ")
                .map { it.toInt() }
                .let { ints ->
                    if (isIncrementingListWithMistake(ints) || isIncrementingListWithMistake(ints.reversed())) {
                        1L
                    } else {
                        0L
                    }
                }.toLong()
        }
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
