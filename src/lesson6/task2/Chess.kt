@file:Suppress("UNUSED_PARAMETER")
package lesson6.task2
val delKing = listOf(Pair(1, 1), Pair(1, 0), Pair(1, -1), Pair(0, 1),
        Pair(0, -1), Pair(-1, 1), Pair(-1, 0), Pair(-1, -1))

val delKnight = listOf(Pair(2, 1), Pair(2, -1), Pair(-2, 1), Pair(-2, -1),
        Pair(1, 2), Pair(1, -2), Pair(-1, 2), Pair(-1, -2))
/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String {
        if (this.column !in 1..8 || this.row !in 1..8) return ""
        return "${(this.column - 1 + 'a'.toInt()).toChar()}${this.row}"
    }
}
/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    if (notation.length != 2) throw IllegalArgumentException()
    val listOfSymbols = listOf('a'..'h' step 1).last()
    val col = listOfSymbols.indexOf(notation[0]) + 1
    val row = notation[1].toInt() - '0'.toInt()
    if (col !in 1..8 || row !in 1..8) throw IllegalArgumentException()
    return Square(col, row)
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int = when {
    (!start.inside() || !end.inside()) -> throw IllegalArgumentException()
    start.column == end.column && start.row == end.row -> 0
    start.column == end.column || start.row == end.row -> 1
    else -> 2
}
/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {
    val mutList = mutableListOf(start)
    val moveNumber = rookMoveNumber(start, end)
    when (moveNumber) {
        0 -> return mutList
        1 -> mutList.add(Square(end.column, end.row))
        2 -> {
            mutList.add(Square(start.column, end.row))
            mutList.add(Square(end.column, end.row))
        }
    }
    return mutList
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int = when {
    !start.inside() || !end.inside() -> throw IllegalArgumentException()
    start == end -> 0
    Math.abs(end.row - start.row) == Math.abs(end.column - start.column) -> 1
    (start.row + start.column) % 2 != (end.row + end.column) % 2 -> -1
    else -> 2
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    val moveNumber = bishopMoveNumber(start, end)
    val mutList = mutableListOf(start)
    when (moveNumber) {
        -1 -> return listOf()
        0 -> return mutList
        1 -> mutList.add(end)
        else -> {
            var a = start.row - start.column
            var b = end.column + end.row
            var y = (a + b) / 2
            var x = y - a
            if (!Square(x, y).inside()) {
                a = start.row + start.column
                b = end.row - end.column
                y = (a + b) / 2
                x = y - b
            }
            mutList.add(Square(x, y))
            mutList.add(end)
        }
    }
    return mutList
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int = TODO()

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> = TODO()

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun knightMoveNumber(start: Square, end: Square): Int {
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    var path = mutableListOf<Square>(start)
    var res = 0
    while (true) {
        if (end in path) break
        path = li(end, path, delKnight)
        res++
    }
    return res
}

fun li(end: Square, path: MutableList<Square>, del: List<Pair<Int, Int>>): MutableList<Square> {
    val res = path.toMutableList()
    for (p in path) {
        for (d in del) {
            val tmp = Square(p.column + d.first, p.row + d.second)
            if (tmp !in res && tmp.inside()) res.add(tmp)
        }
    }
    return res
}

fun liPath(start: Square, end: Square, del: List<Pair<Int, Int>>): List<Square> {
    val desk = arrayOf(
            arrayOf(-1, -1, -1, -1, -1, -1, -1, -1),
            arrayOf(-1, -1, -1, -1, -1, -1, -1, -1),
            arrayOf(-1, -1, -1, -1, -1, -1, -1, -1),
            arrayOf(-1, -1, -1, -1, -1, -1, -1, -1),
            arrayOf(-1, -1, -1, -1, -1, -1, -1, -1),
            arrayOf(-1, -1, -1, -1, -1, -1, -1, -1),
            arrayOf(-1, -1, -1, -1, -1, -1, -1, -1),
            arrayOf(-1, -1, -1, -1, -1, -1, -1, -1)
    )
    var path = mutableListOf<Square>(start)
    var res = 0
    while (true) {
        path.forEach {
            if (desk[it.column - 1][it.row - 1] == -1 || desk[it.column - 1][it.row - 1] > res)
                desk[it.column - 1][it.row - 1] = res
        }
        if (end in path) break
        path = li(end, path, del)
        res++
    }
    val smallest = mutableListOf<Square>(end)
    var point = end
    while (point != start) {
        for (d in del) {
            val tmp = Square(point.column + d.first, point.row + d.second)
            if (!tmp.inside()) continue
            if (desk[tmp.column - 1][tmp.row - 1] == desk[point.column - 1][point.row - 1] - 1) {
                smallest.add(tmp); point = tmp; break
            }
        }
    }
    return smallest
}

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> = liPath(start, end, delKnight).reversed()
