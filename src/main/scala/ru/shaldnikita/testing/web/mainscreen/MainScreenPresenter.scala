package ru.shaldnikita.testing.web.mainscreen

import java.util.concurrent.TimeUnit

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ru.shaldnikita.testing.akka.messages._
import ru.shaldnikita.testing.domain.entities.TestResult
import ru.shaldnikita.testing.web.Question
import ru.shaldnikita.testing.web.question.QuestionForm
import ru.shaldnikita.testing.web.result.ResultForm

import scala.concurrent.ExecutionContext

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
@Component
@Scope("prototype")
class MainScreenPresenter(@Autowired private val testStateActor: ActorRef)(implicit ec: ExecutionContext) {
  private var mainScreen: MainScreen = _

  implicit val timeout: Timeout = Timeout(2, TimeUnit.SECONDS)

  def init(mainScreen: MainScreen): Unit = {
    this.mainScreen = mainScreen
    (testStateActor ? IsFinished).mapTo[Boolean]
      .foreach(_ => {
        case true => finish()
        case false =>
          (testStateActor ? CurrentQuestion)
            .mapTo[Question]
            .foreach(curQuestion => this.mainScreen.replaceQuestionForm(new QuestionForm(curQuestion)))
      })
  }

  def finish(): Unit = {
    saveCurrentQuestionFormState()
    (testStateActor ? Results).mapTo[Array[Question]]
      .foreach(results => {
        testStateActor ! Finish
        this.mainScreen.removeAll()
        this.mainScreen.add(new ResultForm(TestResult(results)))
      })

  }

  def restart(): Unit = {
    (testStateActor ? CurrentQuestion).mapTo[Question]
      .foreach(currentQuestion => {
        testStateActor ! Restart
        this.mainScreen.removeAll()
        this.mainScreen.replaceQuestionForm(new QuestionForm(currentQuestion))
      })

  }

  protected[web] def prevButtonClicked(): Unit = {
    saveCurrentQuestionFormState()
    (testStateActor ? PrevQuestion).mapTo[Question]
      .foreach(prevQuestion => mainScreen.replaceQuestionForm(new QuestionForm(prevQuestion)))
  }

  private def saveCurrentQuestionFormState(): Unit = {
    mainScreen.getCurrentQuestionForm.foreach(form => {
      testStateActor ! SaveCurrentQuestionState(form.currentAnswer)
    })
  }

  protected[web] def nextButtonClicked(): Unit = {
    saveCurrentQuestionFormState()
    (testStateActor ? NextQuestion).mapTo[Question]
      .foreach(nextQuestion => mainScreen.replaceQuestionForm(new QuestionForm(nextQuestion)))
  }
}
