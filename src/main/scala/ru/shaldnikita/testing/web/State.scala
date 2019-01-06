package ru.shaldnikita.testing.web

import com.vaadin.flow.spring.annotation.VaadinSessionScope
import org.springframework.stereotype.Component
import ru.shaldnikita.testing.domain.DataLoader
import ru.shaldnikita.testing.domain.entities.{Question, QuestionResult}

@Component
@VaadinSessionScope
class State {
  var questions: Array[(Question, Option[String])] = random10Questions
  var curIndex = 0
  var finished = false

  def finish() = {
    finished = true
  }

  def reset(): Unit = {
    finished = false
    curIndex = 0
    questions = random10Questions
  }

  private def random10Questions = {
    DataLoader.getRandom10Questions.map(question =>
      (question, Option.empty[String])).toArray
  }

  def currentQuestion = {
    questions(curIndex)._1
  }

  def currentAnswer = {
    questions(curIndex)._2
  }

  def results = {
    questionsWithAnswers.map(qa => QuestionResult(qa._1, qa._2.get))
  }

  def questionsWithAnswers = {
    questions.filter(_._2.isDefined)
  }

  def nextQuestion = {
    val newIndex = if (curIndex == 9) 0 else curIndex + 1
    curIndex = newIndex
    questions(newIndex)
  }

  def prevQuestion = {
    val newIndex = if (curIndex == 0) 9 else curIndex - 1
    curIndex = newIndex
    questions(newIndex)
  }

  def saveCurrentQuestion(answer: Option[String]): Unit = {
    answer.foreach(currentAnswer => {
      val curQuestion = questions(curIndex)._1
      questions(curIndex) = (curQuestion, Some(currentAnswer))
    })
  }
}