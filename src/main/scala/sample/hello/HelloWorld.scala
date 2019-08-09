package sample.hello

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.{Broadcast, FromConfig, RoundRobinPool}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class HelloWorld extends Actor {

  var donesReceived = 0

  def broadcastComplete = donesReceived == 4
  //val router1 = context.actorOf(RoundRobinPool(4).props(Props[Greeter]), "router1")
  val router1: ActorRef = context.actorOf(FromConfig.props(Props[Greeter]), "router1")

  override def preStart(): Unit = {

    router1 ! Broadcast(Greeter.Greet)
  }

  def receive: PartialFunction[Any, Unit] = {
    case Greeter.Done =>
      donesReceived += 1
      //start splitting messages one by one
      if(broadcastComplete)
        context.system.scheduler.schedule(0.milliseconds, 3000.milliseconds, router1, Greeter.Talk)
  }
}

