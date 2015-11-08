import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global
import scala.async.Async.{async, await}

/**
  * Created by Anton on 11/8/2015.
  */

object AsyncAwait {
  def translate1: List[String] = {
    Thread.sleep(1000)
    List("Ok", "Abort")
  }

  def translate2: List[String] = {
    Thread.sleep(2000)
    List("Ok", "Cancel")
  }

  def translate = {
    // TODO : Как заставить пример работать?
    // просто обертка над future
    async {
      val f1 = translate1
      val f2 = translate2
      (await(f1) ::: await(f2)).distinct
    }
  }

  def main(args: Array[String]) {
    translate
    Thread.sleep(10000)
  }
}