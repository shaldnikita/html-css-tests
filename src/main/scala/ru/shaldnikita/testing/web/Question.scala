package ru.shaldnikita.testing.web

import ru.shaldnikita.testing.domain.entities.QuestionData

case class Question(questionData: QuestionData, answer: Option[String]) {

}
