package ru.shaldnikita.testing.domain.entities

import org.apache.commons.codec.binary.Base64

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
object EncodedQuestion {
  def apply(questionText: String, encodedCorrectAnswer: String, answers: List[String]): QuestionData = {
    val decodedCorrectAnswer = new String(Base64.decodeBase64(encodedCorrectAnswer))
    QuestionData(questionText, decodedCorrectAnswer, answers)
  }
}

final case class QuestionData(questionText: String, correctAnswer: String, answers: List[String])
