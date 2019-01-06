package ru.shaldnikita.testing.data.entities

case class Test(questions: List[Question], timer: Timer) {
  def start(): Unit = {
    this.timer.start()
  }
}
