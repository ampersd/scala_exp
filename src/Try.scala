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
    val result: Try[String] = Try("OK")
    println("Try success : " + result)
    val failure = Try(throw new NoSuchElementException)
    println("Try failure : " + failure)

    // recover. восстановление в случае ошибок
    val res = Try(dictionary("студент")) // такой вызов даст Success
    val fail = Try(dictionary("аспирант")) // а тут будет Failure
    val str = fail recover {
      case e: NoSuchElementException => "Нет такого слова в нашем словаре"
    }
    println("Сам результат : " + fail)
    println("Наша обработка (получили не Failure, а Success) : " + str)

    // map идет по позитивной ветке выполнения программы
    // это значит, что если мы пишем res.map(<функция1>).map(<функция2>).map(<функция3>).recover{<обработка ошибок>}
    // т.е. производим некую последовательную цепочку обработки и при этом
    // ошибка произойдет на map(<функция2>), то map(<функция3>) выполняться уже не будет, а мы сразу попадем в блок
    // восстановления (recover)
    // это очень удобная фишка
    val chain1 = res.map(_.toUpperCase).map(_.concat("&WOW")).map(_.reverse).recover{
      case e: NoSuchElementException => "Нет такого элемента - " + res
    }
    println("Идем по положительной ветке : " + chain1)
    // вначале res = Success(student)
    // после цепочки обработки получим Success(WOW&TNEDUTS)
    // т.к. никаких ошибок не было -> recover даже не запускается

    val chain2: Try[String] = fail.map(_.toUpperCase).map(_.concat("&WOW")).map(_.reverse).recover{
      case e: NoSuchElementException => "Нет такого элемента - " + fail
    }
    println("Сразу перейдем к обработке ошибок, минуя все преобразования: ")
    println(chain2)
    // в fail у нас изначально Failure(<описание ошибки>), т.к. в словаре не содержится значения для ключа "аспирант"
    // следовательно нечего преобразовывать через цепочку обработки -> поэтому она сразу пропускается и мы попадаем в recover

    // TODO: Получим Failure. Почему?
    println(res.filter(_ => false))
  }
}