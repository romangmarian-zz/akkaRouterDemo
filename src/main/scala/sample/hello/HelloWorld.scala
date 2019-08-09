package sample.hello

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.{Broadcast, FromConfig, RoundRobinPool}

class HelloWorld extends Actor {

  //ignore the first 3 "done" messages
  var donesReceived = 0

  //val router1 = context.actorOf(RoundRobinPool(4).props(Props[Greeter]), "router1")
  val router1: ActorRef = context.actorOf(FromConfig.props(Props[Greeter]), "router1")

  override def preStart(): Unit = {

    router1 ! Broadcast(Greeter.Greet)
  }

  def receive: PartialFunction[Any, Unit] = {
    case Greeter.Done =>
      donesReceived += 1
      if(donesReceived >= 4)
        router1 ! Greeter.Talk
  }
}

