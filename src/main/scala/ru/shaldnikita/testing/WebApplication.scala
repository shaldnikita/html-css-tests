package ru.shaldnikita.testing

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
  * @author n.shaldenkov on 04.01.2019
  *
  */

@SpringBootApplication
class WebApplication

object WebApplication extends App {
  SpringApplication.run(classOf[WebApplication])
}