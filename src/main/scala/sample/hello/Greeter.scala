package sample.hello

import akka.actor.Actor

object Greeter {
  case object Greet
  case object Talk
  case object Done

}

class Greeter extends Actor {
  def receive: PartialFunction[Any, Unit] = {
    case Greeter.Greet =>
      println("This is a broadcast!")
      sender ! Greeter.Done
    case Greeter.Talk =>
      Thread.sleep(5000)
      println("This message is received in a round-robin fashion. How quaint!")
      sender ! Greeter.Done
  }
}