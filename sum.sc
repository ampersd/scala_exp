def Sum (list : List[Int]) : Int = {
  list match {
    case Nil => 0
    case h :: t => h + Sum(t)
  }
}

def main(args: Array[String]) {
  val list = List(1,2,3,4)
  println(Sum(list))
}

main(null)