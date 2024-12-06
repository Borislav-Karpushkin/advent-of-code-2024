fun main() {

    data class Guard(
        var x: Int = 0,
        var y: Int = 0,
        var yDiff: Int = -1,
        var xDiff: Int = 0,
    ) {

        fun nextX(): Int = x + xDiff
        fun nextY(): Int = y + yDiff

        fun move() {
            x = nextX()
            y = nextY()
        }

        fun turnRight() {
            if (yDiff != 0) {
                xDiff = -yDiff
                yDiff = 0
            } else {
                yDiff = xDiff
                xDiff = 0
            }
        }

        fun visit(log: MutableMap<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>) {
            log.getOrPut(x to y) { mutableSetOf() }.add(xDiff to yDiff)
        }
    }

    fun Map<Pair<Int, Int>, Set<Pair<Int, Int>>>.containsPosition(x: Int, y: Int, xDiff: Int, yDiff: Int): Boolean {
        return get(x to y)?.contains(xDiff to yDiff) == true
    }

    class Simulation(
        private val guard: Guard,
        private val field: MutableList<MutableList<Char>>,
        private val visitedAlready: Map<Pair<Int, Int>, Set<Pair<Int, Int>>> = emptyMap()
    ) {
        val yMax = field.size - 1
        val xMax = field[0].size - 1

        val visitedLog = mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()

        fun tryReachEnd(): Boolean {
            while (guard.nextX() in 0..xMax && guard.nextY() in 0..yMax) {
                guard.visit(visitedLog)
                when (field[guard.nextY()][guard.nextX()]) {
                    '#' -> guard.turnRight()
                    else -> guard.move()
                }

                if (
                    visitedLog.containsPosition(guard.x, guard.y, guard.xDiff, guard.yDiff) ||
                    visitedAlready.containsPosition(guard.x, guard.y, guard.xDiff, guard.yDiff)
                ) {
                    return false
                }
            }
            guard.visit(visitedLog)

            return true
        }
    }

    fun part1(input: List<String>): Int {
        var yGuard = input.indexOfFirst { string -> string.contains("^") }
        var xGuard = input[yGuard].indexOf('^')
        val guard = Guard(xGuard, yGuard)
        val field = input.map { it.toCharArray().toMutableList() }.toMutableList()

        val simulation = Simulation(guard, field)
        simulation.tryReachEnd()

        return simulation.visitedLog.size
    }

    fun part2(input: List<String>): Int {
        var yGuard = input.indexOfFirst { string -> string.contains("^") }
        var xGuard = input[yGuard].indexOf('^')
        val guard = Guard(xGuard, yGuard)
        val field = input.map { it.toCharArray().toMutableList() }.toMutableList()
        val yMax = field.size - 1
        val xMax = field[0].size - 1
        var traps = 0

        val visitedLog = mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()

        while (guard.nextX() in 0..xMax && guard.nextY() in 0..yMax) {
            guard.visit(visitedLog)
            when (field[guard.nextY()][guard.nextX()]) {
                '#' -> guard.turnRight()
                else -> {
                    if (!visitedLog.contains(guard.nextX() to guard.nextY())) {
                        val tmp = field[guard.nextY()][guard.nextX()]
                        field[guard.nextY()][guard.nextX()] = '#'

                        val ghost = guard.copy()
                        ghost.turnRight()
                        if (!Simulation(ghost, field, visitedLog).tryReachEnd()) ++traps
                        field[guard.nextY()][guard.nextX()] = tmp
                    }

                    guard.move()
                }
            }
        }

        return traps
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
