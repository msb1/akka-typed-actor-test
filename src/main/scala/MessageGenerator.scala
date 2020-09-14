import java.text.SimpleDateFormat
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

case class WebMessage(msgType: String, msgId: String, msgTime: String, user: String, value: String)

object WebEvent {
  val CHAT_MESSAGE = "CHAT_MESSAGE"
  val DATA_MESSAGE = "DATA_MESSAGE"
}

object MessageGenerator {

  sealed trait Command

  case object Generate extends Command

  case object Terminate extends Command

  val counter = new AtomicInteger(0)

  def apply():
  Behavior[Command] = Behaviors.setup(context => {
    context.log.info("MessageGenerator OBJECT...")
    new MessageGenerator(context)
  })
}

class MessageGenerator(context: ActorContext[MessageGenerator.Command]) extends AbstractBehavior[MessageGenerator.Command](context) {

  import MessageGenerator._

  // implicit val system = context.system
  context.log.info("MessageGenerator CLASS...")
  private val timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  override def onMessage(msg: MessageGenerator.Command): Behavior[MessageGenerator.Command] =
    msg match {
      case Generate => {
        val uuid = UUID.randomUUID().toString
         val num = counter.incrementAndGet()
         context.log.info(s"Message $num with uuid: $uuid")
        this
      }

      case Terminate => {
        val uuid = UUID.randomUUID().toString
        context.log.info(s"Message Terminate with uuid: $uuid")
        this
      }
    }
}

