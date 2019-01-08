package ru.shaldnikita.testing.web

import akka.actor.{Actor, ActorLogging}
import ru.shaldnikita.testing.akka.messages._
import ru.shaldnikita.testing.domain.DataLoader

class TestStateActor extends Actor with ActorLogging {
  var questions: Array[Question] = _
  var curIndex: Int = _
  var finished: Boolean = _

  override def receive: PartialFunction[Any, Unit] = {
    case Start =>
      log.info("Start")
      init()
    case Finish =>
      log.info("Finish")
      finish()
    case Restart =>
      log.info("Restart")
      init()

    case Results => sender() ! results
    case IsFinished => sender() ! finished

    case answer: SaveCurrentQuestionState => saveCurrentQuestion(answer.answer)

    case CurrentQuestion => sender() ! currentQuestion
    case PrevQuestion => sender() ! prevQuestion
    case NextQuestion => sender() ! nextQuestion

    case _ => log.warning("Wrong message")
  }


  override def postStop(): Unit = {
    log.info("STOP")
    super.postStop()
  }

  private def finish(): Unit = {
    finished = true
  }

  private def init(): Unit = {
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

  private def saveCurrentQuestion(currentAnswer: String): Unit = {
    val curQuestion = questions(curIndex).questionData
    questions(curIndex) = Question(curQuestion, Some(currentAnswer))
  }
}