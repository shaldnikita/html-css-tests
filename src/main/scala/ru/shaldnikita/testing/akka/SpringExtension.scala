package ru.shaldnikita.testing.akka

import akka.actor.Extension
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class SpringExtension(applicationContext: ApplicationContext) extends Extension {

  import akka.actor.Props

  def props(actorBeanName: String): Props = Props.create(classOf[SpringActorProducer], applicationContext, actorBeanName)

}
