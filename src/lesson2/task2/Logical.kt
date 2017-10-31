@file:Suppress("UNUSED_PARAMETER")
package lesson2.task2

import lesson1.task1.sqr
import java.lang.Math.sqrt

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
        sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int): Boolean =
        number / 1000 + number / 100 % 10 == number % 10 + number / 10 % 10

/**
 * Простая
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean =
        x1 == x2 || y1 == y2 || Math.abs(x1 - x2) == Math.abs(y1 - y2)
/**
 * Средняя
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(x1: Double, y1: Double, r1: Double,
                 x2: Double, y2: Double, r2: Double): Boolean =
        r2 - sqrt(sqr(x1 - x2) + sqr(y1 - y2)) >= r1
/**
 * Функция для нахлждения среднего числа из 3
 */

fun middleOfThree(a: Int, b: Int, c: Int): Int {
    return when {
        b >= a && a >= c || c >= a && a >= b -> a
        a >= b && b >= c || c >= b && b >= a -> b
        a >= c && c >= b || b >= c && c >= a -> c
        else -> 0
    }
}
/**
 * Средняя
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */


fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean {
    var minOfBrick = minOf(a, b, c)
    var middleOfBrick = middleOfThree(a, b, c)
    var minOfPas = minOf(r, s)
    var maxOfPas = maxOf(r, s)
    if (minOfBrick <= minOfPas && middleOfBrick <= maxOfPas) return true
    else return false
}