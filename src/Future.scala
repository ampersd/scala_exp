import scala.concurrent.Future
// для параллельной работы нужно указать пул потоков - ExecutionContext, мы делаем это неявно через следующий импорт
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

/**
  * Created by Anton on 11/8/2015.
  */

object FutureObj {

  def FutureResult: Unit = {
    // инициализоровали фьючу, с этого момента она ищет свободный поток в пуле потоков, чтобы начать свое параллельное выполнение
    // Future начинает выполняться тогда, когда инициализирована
    // в Erlang вытесняющий параллелизм (каждому блоку отводится одинаковое время на выполнение, не успел за это время - ресурсы отдаются другой задаче, а эта нить засыпает)
    // в Scala если выполнение зашло в блок Future, то выполнится целиком
    val future = Future{
      Thread.sleep(2000)
      "OK"
    }

    // можем подписаться на результат, когда Future заканчивает свое выполнение, она возвращает Try
    // результат во Future записывается только один раз и не может быть переписан
    // мы можем как обработать и Success и Failure вместе, как в примере ниже
    future.onComplete {
      case Success(s) => println("onComplete : " + s)
      case Failure(f) => println("onComplete : " + f)
    }
    // так и сделать это по отдельности
    future.onSuccess{
      case r => println("onSuccess : " +  r)
    }
    future.onFailure{
      case f => println("onFailure : " +  f)
    }
  }


  def main(args: Array[String]) {
    FutureResult
    // чтобы программа сразу не завершилась, отправляем основной поток в спячку на 5 секунд
    Thread.sleep(5000)
  }


}