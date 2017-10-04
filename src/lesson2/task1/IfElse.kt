@file:Suppress("UNUSED_PARAMETER")
package lesson2.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import lesson1.task1.trackLength

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -Math.sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    val y3 = Math.max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -Math.sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String = when {
    ( ( age / 10) == 1 ) || ( ( age / 10  ) == 11 ) -> "$age лет"
    ( ( age % 10 ) == 1 ) -> "$age год"
    ( ( age % 10 ) > 1 ) && ( ( age % 10 ) < 5 ) -> "$age года"
    ( ( age % 10 ) >= 5 ) || ( ( age % 10 ) == 0 ) -> "$age лет"
    else -> "Такого возраста не существует"
}



/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(t1: Double, v1: Double,
                   t2: Double, v2: Double,
                   t3: Double, v3: Double): Double  {
    val n = (t1 * v1 + t2 * v2 + t3 * v3) / 2
    return  when {
        ((t1 * v1) >= n) -> n / v1
        ((t1 * v1) < n) && (( t1 * v1 + t2 * v2) >= n) -> t1 + ( n - t1 * v1 ) / v2
        else -> t1 + t2 + ( n - t1 * v1 - t2 * v2 ) / v3
    }
}
/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int,
                       rookX1: Int, rookY1: Int,
                       rookX2: Int, rookY2: Int): Int = when {
    ( kingX == rookX1 && kingY == rookY2 ) || ( kingY == rookY1 && kingX == rookX2 ) -> 3
    ( kingX == rookX1 ) || ( kingY == rookY1 ) -> 1
    ( kingX == rookX2 ) || ( kingY == rookY2 ) -> 2
    else -> 0
}

/**
 * Простая
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(kingX: Int, kingY: Int,
                          rookX: Int, rookY: Int,
                          bishopX: Int, bishopY: Int): Int = when {
    sqr( bishopX.toDouble() - kingX ) == sqr( bishopY.toDouble() - kingY ) && ( ( kingX == rookX )|| ( kingY == rookY ) ) -> 3
    ( kingX == rookX ) || ( kingY == rookY) -> 1
     sqr( bishopX.toDouble() - kingX ) == sqr( bishopY.toDouble() - kingY ) -> 2
    else -> 0
}

/**
 * Простая
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int =TODO()



/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int = when {
    c < b && b < d && a < c  -> b - c
    c == b -> 0
    a < c && d < b -> d - c
    c < a && d < b && a < d  -> d - a
    c < a && b < d -> b - a
    else -> -1
}
