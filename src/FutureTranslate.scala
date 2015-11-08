import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

/**
  * Created by Anton on 11/8/2015.
  */

object FutureTranslate {
  def translate1(word: String): List[String] = {
    Thread.sleep(2000)
    List(word, "1","2","3")
  }

  def translate2(word: String): List[String] = {
    Thread.sleep(3000)
    List(word, "3","4","4","5")
  }

  def translate(word: String): Future[List[String]] = {
    val result1: Future[List[String]] = Future{
      translate1(word)
    }
    val result2: Future[List[String]] = Future{
      translate2(word)
    }
    // for - синхронизация потоков, синтаксический сахар над flatMap
    // если одно из извлечений (result1 или result2 упадет, код в yield не выполнится)
    // недостаток for - можно использовать монады только одного типа (например, Future, но уже нельзя в этом случае использовать Sum)
    for {
      list1 <- result1
      list2 <- result2
    } yield {
      (list1 ::: list2).distinct
    }
  }

  // ещё один способ синхронизации фьюч (помимо for) - использование Future.sequence, т.е. объединение мелких фьюч в одну большую
  def translateSequence (word: String) = {
    val result1: Future[List[String]] = Future{
      translate1(word)
    }
    val result2: Future[List[String]] = Future{
      translate2(word)
    }
    // даёт тот же результат, что и translate
    Future.sequence(Seq(result1, result2)).map{
      _.flatten.toList.distinct
    }
  }

  def main(args: Array[String]) {
    println(translate("бог"))
    val words = List("Ярило","Зевс")
    // обращаемся к списку, получаем на самом деле промисы
    // TODO: почему у меня записано что a : Try[Future[List[String]]] ? Как добиться этого?
    val a: List[Future[List[String]]] = words.map(translate)
    println("Промисы : " + a)

    val startTime = System.currentTimeMillis()
    // сразу можем привязать обработку результатов
    words.map(translate).foreach{
      _ onComplete{
        case Success(list) => println("Success : " + list)
          // TODO: что-то здесь не работает должным образом, слишком большие тайминги получаются
          val endTime = System.currentTimeMillis()
          println("Operations took " + (endTime - startTime) + " milli seconds")
        case Failure(e) => println("Failure : " + e)
      }
    }


    // то же самое, с translateSequence
    words.map(translateSequence).foreach{
      _ onComplete{
        case Success(list) => println("Success2 : " + list)
        case Failure(e) => println("Failure2 : " + e)
      }
    }
    Thread.sleep(15000)
  }
}