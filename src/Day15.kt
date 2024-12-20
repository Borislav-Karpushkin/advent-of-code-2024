fun main() {

    data class Vec2(val x: Int, val y: Int) {
        fun plus(v: Vec2) = Vec2(x + v.x, y + v.y)
    }

    open class Obj
    class Wall: Obj()
    class Box: Obj()

    class Simulation(
        val xMax: Int,
        val yMax: Int,
        val map: MutableMap<Vec2, Obj> = mutableMapOf(),
        var robotPosition: Vec2 = Vec2(-1, -1),
        val commands: MutableList<Vec2> = mutableListOf(),
    ) {
        fun start(afterMove: () -> Unit) {
            for (direction in commands) {
                if (canMove(robotPosition, direction)) {
                    val newPosition = robotPosition.plus(direction)
                    moveBoxes(newPosition, direction)
                    robotPosition = newPosition
                }

                afterMove.invoke()
            }
        }

        fun canMove(position: Vec2, direction: Vec2): Boolean {
            val newPosition = position.plus(direction)
            return when (map[newPosition]) {
                is Wall -> false
                is Box -> canMove(newPosition, direction)
                else -> true
            }
        }

        fun moveBoxes(position: Vec2, direction: Vec2) {
            if (map[position] == null) return

            val nextPosition = position.plus(direction)
            if (map[nextPosition] is Box) {
                moveBoxes(nextPosition, direction)
            }

            map.remove(position)
            map[nextPosition] = Box()
        }
    }

    val up = Vec2(0, -1)
    val down = Vec2(0, 1)
    val right = Vec2(1, 0)
    val left = Vec2(-1, 0)

    fun parseInput(input: List<String>): Simulation {
        val result = Simulation(xMax = input[0].length - 1, yMax = input.count { it.startsWith('#') } - 1)

        input.forEachIndexed { i, string ->
            if (string.startsWith('#')) {
                string.forEachIndexed { j, char ->
                    when (char) {
                        '#' -> result.map[Vec2(j, i)] = Wall()
                        'O' -> result.map[Vec2(j, i)] = Box()
                        '@' -> result.robotPosition = Vec2(j, i)
                    }
                }
            } else {
                string.forEach { char ->
                    when (char) {
                        '^' -> result.commands.addLast(up)
                        'v' -> result.commands.addLast(down)
                        '<' -> result.commands.addLast(left)
                        '>' ->result.commands.addLast(right)
                    }
                }
            }
        }

        return result
    }

    fun part1(input: List<String>): Int {
        val simulation = parseInput(input)
        simulation.start {
            for (y in 0..simulation.yMax) {
                (0..simulation.xMax).map { x ->
                    val position = Vec2(x, y)
                    val obj = simulation.map[position]
                    when {
                        obj is Wall -> '#'
                        obj is Box -> 'O'
                        position == simulation.robotPosition -> '@'
                        else -> ' '
                    }
                }.joinToString("").println()
            }
            println("----------------------------------------------------")
        }
        return simulation.map
            .toList()
            .filter { (_, obj) -> obj is Box }
            .sumOf { (pos, _) ->
                pos.y * 100 + pos.x
            }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
