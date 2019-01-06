package ru.shaldnikita.testing.domain.entities

import ru.shaldnikita.testing.web.Question

case class TestResult(questionResults: Iterable[Question]) {
  def answers: Iterable[Option[String]] = questionResults.map(_.answer)

  def correctAnswers: Iterable[Question] = questionResults.filter(qr => qr.answer.getOrElse("") == qr.questionData.correctAnswer)

  def wrongAnswers: Iterable[Question] = questionResults.filter(qr => qr.answer.getOrElse("") != qr.questionData.correctAnswer)

  def results: Iterable[AnswerPair] = questionResults.map(result => AnswerPair(result.answer.getOrElse(""), result.questionData.correctAnswer))


}

case class AnswerPair(answer: String, correctAnswer: String)