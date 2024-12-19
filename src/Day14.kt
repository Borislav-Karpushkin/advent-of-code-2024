import java.io.FileOutputStream

fun main() {

    data class Point(val x: Int, val y: Int)

    data class Robot(val position: Point, val velocity: Point)

    fun parseInput(input: List<String>): List<Robot> {
        return input.map { string ->
            val (pos, vel) = string.split(' ')
            val (posX, posY) = pos.removePrefix("p=").split(',')
            val (velX, velY) = vel.removePrefix("v=").split(',')
            Robot(
                position = Point(posX.toInt(), posY.toInt()),
                velocity = Point(velX.toInt(), velY.toInt())
            )
        }
    }

    fun part1(input: List<String>): Int {
        val seconds = 100
        // val xMax = 11
        // val yMax = 7
        val xMax = 101
        val yMax = 103
        return parseInput(input).map { robot ->
            Point(
                x = ((robot.position.x + robot.velocity.x * seconds) % xMax + xMax) % xMax,
                y = ((robot.position.y + robot.velocity.y * seconds) % yMax + yMax) % yMax
            )
        }.groupBy { p ->
            when {
                p.x < xMax / 2 && p.y < yMax / 2 -> 0
                p.x < xMax / 2 && p.y > yMax / 2 -> 1
                p.x > xMax / 2 && p.y < yMax / 2 -> 2
                p.x > xMax / 2 && p.y > yMax / 2 -> 3
                else -> null
            }
        }
            .mapNotNull { (k, v) -> k?.let { v.size } }
            .reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        FileOutputStream("puzzle_output.txt").use { outputStream ->
            val xMax = 101
            val yMax = 103
            val robots = parseInput(input)
            for (seconds in 0..100_000) {
                val futurePositions = robots.map { robot ->
                    Point(
                        x = ((robot.position.x + robot.velocity.x * seconds) % xMax + xMax) % xMax,
                        y = ((robot.position.y + robot.velocity.y * seconds) % yMax + yMax) % yMax
                    )
                }.toSet()
                (0..<yMax).forEach { y ->
                    (0..<xMax).map { x ->
                        if (futurePositions.contains(Point(x, y))) {
                            '*'
                        } else {
                            ' '
                        }
                    }.joinToString("")
                        .also { string ->
                            outputStream.write((string + "\n").toByteArray())
                        }.println()
                }
                val msg = "| $seconds -----------------------------------------------------------------------------"
                outputStream.write((msg + "\n").toByteArray())
                println(msg)
                //Thread.sleep(500L)
            }
        }
        return 7858
    }

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
