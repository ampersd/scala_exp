import scala.util.Try

/**
  * Created by Anton on 11/7/2015.
  */

object TryProgram {
  def main(args: Array[String]) {
    val dictionary = Map(
    "университет" -> "university",
    "студент" -> "student")
    println("Создали словарь : " + dictionary)

    // get вернет None, если такого элемента не будет
    // и Some(<элемент>), если есть
    // т.е. возвращается Option
    println("Есть элемент : " + dictionary.get("студент"))
    println("Нет элемента : " + dictionary.get("аспирант"))

    // Объект Try может вернуть либо Success, либо Failure
    val result = Try("OK")
    println("Try success : " + result)
    val fail = Try(throw new NoSuchElementException)
    println("Try failure : " + fail)



  }
}