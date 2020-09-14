import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

import MessageGenerator.Generate
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

import scala.concurrent.duration.DurationInt

object TestActorManager {

  sealed trait Command

  case object StartMessages extends Command

  private case object GenMessages extends Command

  private case object MessageTimerKey

  // val counter = new AtomicInteger(0)

  def apply(): Behavior[Command] =
    Behaviors.setup(context => {
      context.log.info("TestActorManager OBJECT...")
      new TestActorManager(context)
    })
}


class TestActorManager(context: ActorContext[TestActorManager.Command])
  extends AbstractBehavior[TestActorManager.Command](context) {

  import TestActorManager._
  context.log.info("TestActorManager CLASS...")
  val genMessage = context.spawn(MessageGenerator(), "MessageGenerator")

  override def onMessage(msg: Command): Behavior[Command] =
    msg match {
      case StartMessages => {
        Behaviors.withTimers[Command] { timers =>
          timers.startTimerWithFixedDelay(MessageTimerKey, GenMessages, 1.seconds)
          this
        }
      }
      case GenMessages =>
        // val uuid = UUID.randomUUID().toString
        // val num = counter.incrementAndGet()
        // context.log.info(s"Message $num with uuid: $uuid")
        genMessage ! Generate
        this

      case _ => Behaviors.empty
    }
}





