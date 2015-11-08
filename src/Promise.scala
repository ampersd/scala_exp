import scala.concurrent.{Promise, Future}
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by Anton on 11/8/2015.
  */

object PromiseObj {
  def translate(word: String) = {

    val promise = Promise[List[String]]()

    Future {
      Thread.sleep(3000)
      List("OK", "OK", "OK")
      // TODO : почему здесь ошибка?
    } onSuccess {
      list => promise.trySuccess(list)
    }

    // promise - у этого объекта есть Future, т.е. ссылка по которой можно подписаться и выполнить тогда, когда
    // promise перейдет в то или иное состояние (либо success, либо failure)
    val reference = promise.future

    // TODO : и здесь тоже
    reference.onSuccess{
      list => println(list)
    }
  }

  def main(args: Array[String]) {
    translate("НеважноКакоеСлово")
    Thread.sleep(10000)
  }
}