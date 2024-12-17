import kotlin.math.abs

fun main() {

    data class Point(val x: Int, val y: Int)

    data class Line(val start: Point, val end: Point) {
        fun vector() = Point(end.x - start.x, end.y - start.y)
    }

    data class Region(
        val char: Char,
        val points: MutableSet<Point> = mutableSetOf()
    ) {

        // actually calculates 90 degree angles, haha
        fun calculateSides(): Int {
            val edges = mutableSetOf<Line>()
            // build edges. Count only lines that exists only ones (twice means line is between letters)
            points.forEach { p ->
                listOf(
                    Line(p, Point(p.x + 1, p.y)),
                    Line(p, Point(p.x, p.y + 1)),
                    Line(Point(p.x + 1, p.y), Point(p.x + 1, p.y + 1)),
                    Line(Point(p.x, p.y + 1), Point(p.x + 1, p.y + 1)),
                ).forEach { line ->
                    if (edges.contains(line)) {
                        edges.remove(line)
                    } else {
                        edges.add(line)
                    }
                }
            }

            val edgesByStart = edges.groupBy { it.start }
            val edgesByEnd = edges.groupBy { it.end }

            return edges.flatMap { listOf(it.start, it.end) }.distinct().sumOf { point ->
                val edges = (edgesByStart[point].orEmpty() + edgesByEnd[point].orEmpty()) // 4 edges -> 2 angles
                val vectors = edges.map { it.vector() }.distinct() // 2 vectors - 2 directions
                (vectors.size - 1) * edges.size / 2
            }
        }

        // count of absent point's neighbors
        fun calculatePerimeter(): Int {

            return points.sumOf { (x, y) ->
                listOf(
                    hasNoPoint(x + 1, y),
                    hasNoPoint(x - 1, y),
                    hasNoPoint(x, y + 1),
                    hasNoPoint(x, y - 1),
                ).sum()
            }
        }

        fun calculateSquare(): Int {
            return points.size
        }

        private fun hasNoPoint(x: Int, y: Int): Int {
            return if (points.contains(Point(x, y))) 0 else 1
        }
    }

    // BFS
    fun createRegion(map: List<List<Char>>, start: Point): Region {
        fun addPoint(region: Region, p: Point) {
            if (map.getOrNull(p.y)?.getOrNull(p.x) != region.char) {
                return
            }

            if (!region.points.add(p)) {
                return
            }

            addPoint(region, Point(p.x - 1, p.y))
            addPoint(region, Point(p.x, p.y + 1))
            addPoint(region, Point(p.x, p.y - 1))
            addPoint(region, Point(p.x + 1, p.y))
        }

        val region = Region(map[start.y][start.x])
        addPoint(region, start)
        return region
    }

    fun buildRegions(input: List<String>): MutableList<Region> {
        val map = input.map { string -> string.toList() }
        val alreadyVisited = mutableSetOf<Point>()
        val regions: MutableList<Region> = mutableListOf()

        map.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                val point = Point(x, y)
                if (!alreadyVisited.contains(point)) {
                    val region = createRegion(map, point)
                    alreadyVisited.addAll(region.points)
                    regions += region
                }
            }
        }

        return regions
    }

    fun part1(input: List<String>): Int {
        val regions = buildRegions(input)

        return regions.sumOf { region ->
            region.calculateSquare() * region.calculatePerimeter()
        }
    }

    fun part2(input: List<String>): Int {
        val regions = buildRegions(input)

        return regions.sumOf { region ->
            region.calculateSquare() * region.calculateSides()
        }
    }

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
