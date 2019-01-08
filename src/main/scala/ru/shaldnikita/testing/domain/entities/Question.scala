package ru.shaldnikita.testing.domain.entities

import java.nio.charset.Charset

import org.apache.commons.codec.binary.Base64

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
object Question {
  def apply(question: String, correctAnswer: String, answers: List[String]): Question = {
    val encodedCorrectAnswer = new String(Base64.decodeBase64(correctAnswer), Charset.forName("UTF-8"))
    new Question(question, encodedCorrectAnswer, answers)
  }
}

final case class Question(question: String, correctAnswer: String, answers: List[String])
