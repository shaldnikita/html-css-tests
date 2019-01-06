package ru.shaldnikita.testing.web

import akka.actor.{Actor, ActorLogging}
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ru.shaldnikita.testing.akka.messages._
import ru.shaldnikita.testing.domain.DataLoader

@Component
@Scope("prototype")
class TestStateActor extends Actor with ActorLogging {
  var questions: Array[Question] = random10Questions
  var curIndex = 0
  var finished = false

  override def receive: PartialFunction[Any, Unit] = {
    case _: Finish => finish()
    case _: Restart => reset()
    case _: Results => sender() ! results
    case _: CurrentQuestion => sender() ! currentQuestion
    case _: PrevQuestion => sender() ! prevQuestion
    case _: NextQuestion => sender() ! nextQuestion

    case _ => log.warning("Wrong message")
  }

  private def finish(): Unit = {
    finished = true
  }

  private def reset(): Unit = {
    finished = false
    curIndex = 0
    questions = random10Questions
  }

  private def random10Questions = {
    DataLoader.getRandom10Questions.map(question =>
      Question(question, Option.empty[String])).toArray
  }

  private def currentQuestion = {
    questions(curIndex)
  }

  private def results = {
    questionsWithAnswers
  }

  private def questionsWithAnswers = {
    questions.filter(_.answer.isDefined)
  }

  private def nextQuestion = {
    val newIndex = if (curIndex == 9) 0 else curIndex + 1
    curIndex = newIndex
    questions(newIndex)
  }

  private def prevQuestion = {
    val newIndex = if (curIndex == 0) 9 else curIndex - 1
    curIndex = newIndex
    questions(newIndex)
  }

  private def saveCurrentQuestion(answer: Option[String]): Unit = {
    answer.foreach(currentAnswer => {
      val curQuestion = questions(curIndex).questionData
      questions(curIndex) = Question(curQuestion, Some(currentAnswer))
    })
  }
}