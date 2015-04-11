import scala.actors.Actor

case class Inc(amount: Int)

case class Value()
case class Response(x:Any)

class Counter extends Actor {
  var counter: Int = 0

  def act() = {
    loop {
      react {
        case Inc(amount) =>
          println(s"the value is $amount")
          counter += amount
          sender ! Response(amount)
        case Value() =>
          println("Total value is " + counter)
          exit()
      }
    }
  }
}

object ActorTest {

  def main(agrs: Array[String]): Unit = {
    val counter = new Counter
    counter.start()

    for (i <- 1 to 10) {
      counter !? Inc(i) match {
        case _ => println(s"response...")
      }
    }
    Thread.sleep(1)
    counter ! Value()
  }

}