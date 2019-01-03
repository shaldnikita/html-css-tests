package ru.shaldnikita.web.pojo

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
final case class Question(question: String, var correctAnswer: String, answers: List[String]) {
}
