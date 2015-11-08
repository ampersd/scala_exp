import scala.annotation.tailrec

/**
 * Created by Anton on 11/7/2015.
 */

object Program {
  // Первый способ вычисления суммы - обычная рекурсия, возможно переполнение стека
  def Sum (list : List[Int]) : Int = {
    list match { // match - это Pattern Matching
      case Nil => 0
      case h :: t => h + Sum(t) // задали рекурсию, где h - первый элемент, t - список оставшихся
    }
  }

  // Второй способ - через хвостовую рекурсию, хвостовая рекурсия всегда разворачивается в обычный цикл
  // в хвостовой рекурсии обязательно нужен аккумулятор, переменная, которая будет хранить значение
  @tailrec // стандарт документирования, который позволяет сказать, что эта функция задумывалась именно как хвостовая рекурсия
  def SumTail (list: List[Int], acc: Int = 0) : Int = { // инициализируем acc значением по-умолчанию
    list match {
      case Nil => acc
      case h :: t => SumTail(t, acc + h)
    }
  }

  // Третий способ - использование reduceLeft
  def SumReduceLeft (list: List[Int]) : Int = {
    // предположим на вход подается список 1,2,3,4
    // как работает reduceLeft
    // Шаг 1: 3, 3, 4
    // Шаг 2: 6, 4
    // Шаг 3: 10

    //// list.reduceLeft(_ + _)

    // Проблема метода: будет выдавать ошибку, если список окажется пустым
    // Как исправить? дополнить изначально список слева нулем
    val _list = 0 :: list
    _list.reduceLeft(_ + _)
  }

  // Третий способ, дубль 2 - использование foldLeft
  def SumFoldLeft (list: List[Int]) : Int = {
    // по сути это аналог
    //    val _list = 0 :: list
    //    _list.reduceLeft(_ + _)
    list.foldLeft(0)(_ + _)  // сначала записываем начальное значение, затем операцию
  }

  def main(args: Array[String]) {
    val list = List(1,2,3,4)
    println("Список: " + list)
    println("Обычная рекурсия: " + Sum(list))
    println("Хвостовая рекурсия: " + SumTail(list)) // для хвостовой рекурсии необходимо задать начальное значение аккумулятора
    println("Использование функции reduceLeft: " + SumReduceLeft(list))
    println("Использование функции foldLeft: " + SumFoldLeft(list))
  }
}