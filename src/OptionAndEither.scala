/**
  * Created by Anton on 11/7/2015.
  */

object OptionAndEither {
  def main(args: Array[String]) {
    // Объект Option может вернуть либо контейнер Some указанного типа, либо None
    val opt = Option(x = 5)
    println("Some : " + opt)

    // Either - позволяет самому задать оба типа, которые могут быть возвращены
    // Left(значение) или Right(значение)
    // Try - урезанный Either, т.к.
    // Success(значение) или Failure(Exception)
    // Try - более полный Option, т.к. мы можем обработать ситуацию с None, обработать exception
  }
}