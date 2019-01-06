package ru.shaldnikita.testing.domain.entities

import ru.shaldnikita.testing.web.Question

case class Test(questions: List[Question], timer: Timer) {
  def start(): Unit = {
    this.timer.start()
  }
}
