package ru.shaldnikita.testing.data.entities

case class TestResult(questionResults: Iterable[QuestionResult]) {
  def answers = questionResults.map(_.answer)

  def correctAnswers = questionResults.filter(qr => qr.answer == qr.question.correctAnswer)

  def wrongAnswers = questionResults.filter(qr => qr.answer != qr.question.correctAnswer)

  def results = questionResults.map(result => AnswerPair(result.answer, result.question.correctAnswer))


}


case class QuestionResult(question: Question, answer: String)

case class AnswerPair(answer: String, correctAnswer: String)