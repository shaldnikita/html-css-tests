package ru.shaldnikita.testing.akka

import akka.actor.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.{Bean, Configuration, Lazy}

@Configuration
@Lazy
class AkkaConfig(applicationContext: ApplicationContext,
                 springExtension: SpringExtension) {

  @Bean
  def actorSystem: ActorSystem = {
    ActorSystem.create("KMT", akkaConfiguration)
  }

  @Bean
  def akkaConfiguration: Config = {
    ConfigFactory.load
  }
}
