package sample.hello

import akka.actor.Actor

import scala.collection.mutable
import scala.util.Random

object Greeter {
  case object Greet
  case object Talk
  case object Done
  var ids = mutable.Stack[Int](1, 2, 3, 4)
}

class Greeter extends Actor {

  val id = Greeter.ids.pop()

  def receive: PartialFunction[Any, Unit] = {
    case Greeter.Greet =>
      println(s"Broadcast received by Greeter#$id!")
      sender ! Greeter.Done
    case Greeter.Talk =>
      Thread.sleep(3000 + Random.nextInt(2000))
      println(s"This message was received in a round-robin fashion by Greeter#$id. How quaint!")
  }
}