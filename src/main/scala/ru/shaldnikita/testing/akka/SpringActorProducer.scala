package ru.shaldnikita.testing.akka

import akka.actor.{Actor, IndirectActorProducer}
import org.springframework.context.ApplicationContext

class SpringActorProducer(applicationContext: ApplicationContext,
                          actorBeanName: String) extends IndirectActorProducer {


  override def produce(): Actor = {
    applicationContext.getBean(actorBeanName).asInstanceOf[Actor]
  }

  override def actorClass: Class[_ <: Actor] = {
    applicationContext.getType(actorBeanName).asInstanceOf[Class[_ <: Actor]]
  }
}
