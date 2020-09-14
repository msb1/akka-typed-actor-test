import TestActorMain.{GracefulShutdown, StartCommand}
import TestActorManager.StartMessages
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}

import scala.io.StdIn

object Main {

  def main(args: Array[String]) {

    implicit val system = ActorSystem(Behaviors.empty, "testActors")
    implicit val executionContext = system.executionContext
    system.log.info("TestActors Main started...")

    val testActorMain: ActorRef[TestActorMain.Command] = system.systemActorOf(TestActorMain(), "TestActorMain")
    testActorMain ! StartCommand

    system.log.info("Press RETURN to stop...")
    StdIn.readLine()
    testActorMain ! GracefulShutdown
    system.terminate()
  }
}

object TestActorMain {

  sealed trait Command
  case object StartCommand extends Command
  case object GracefulShutdown extends Command

  def apply(): Behavior[Command] = Behaviors
    .receive[Command] { (context, message) =>
      message match {
        case StartCommand =>
          val testActorManager = context.spawn(TestActorManager(), "TestActorManager")
          testActorManager ! StartMessages
          Behaviors.same
        case GracefulShutdown =>
          Behaviors.stopped { () =>
            context.log.info("Initiating graceful shutdown in TestActorMain...")
          }
      }
    }
}



