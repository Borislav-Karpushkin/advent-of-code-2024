import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val arr1 = mutableListOf<Int>()
        val arr2 = mutableListOf<Int>()

        input.forEach { line ->
            val (firstNum, secondNum) = line.split("\\s+".toRegex())
            arr1 += firstNum.toInt()
            arr2 += secondNum.toInt()
        }

        arr2.sort()
        return arr1.sorted().mapIndexed { i, n ->
            abs(arr2[i] - n)
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val arr1 = mutableListOf<Int>()
        val arr2 = mutableMapOf<Int, Int>()

        input.map { line ->
            val (firstNum, secondNum) = line.split("\\s+".toRegex())
            firstNum.toInt() to secondNum.toInt()
        }.forEach { (firstNum, secondNum) ->
            arr1 += firstNum
            arr2[secondNum] = arr2.getOrDefault(secondNum, 0).inc()
        }

        return arr1.sumOf { it * (arr2[it] ?: 0) }
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
