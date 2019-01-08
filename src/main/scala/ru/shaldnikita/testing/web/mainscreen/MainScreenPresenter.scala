package ru.shaldnikita.testing.web.mainscreen

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.vaadin.flow.component.UI
import com.vaadin.flow.spring.annotation.VaadinSessionScope
import javax.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.shaldnikita.testing.akka.messages._
import ru.shaldnikita.testing.domain.entities.TestResult
import ru.shaldnikita.testing.web.question.QuestionForm
import ru.shaldnikita.testing.web.result.ResultForm
import ru.shaldnikita.testing.web.{Question, TestStateActor}

import scala.concurrent.ExecutionContext

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
@Component
@VaadinSessionScope
class MainScreenPresenter(@Autowired actorSystem: ActorSystem) {
  private var mainScreen: MainScreen = _
  implicit val ec: ExecutionContext = actorSystem.dispatcher
  private val testStateActor: ActorRef = actorSystem.actorOf(Props[TestStateActor])

  implicit val timeout: Timeout = Timeout(2, TimeUnit.SECONDS)
  private var ui: UI = _

  testStateActor ! Start

  @PreDestroy
  def beforeDelete(): Unit = {
    this.testStateActor ! PoisonPill
  }

  protected[web] def init(mainScreen: MainScreen, ui: UI): Unit = {
    this.mainScreen = mainScreen
    this.ui = ui

    val isTestFinished = (testStateActor ? IsFinished).mapTo[Boolean]
    isTestFinished.map {
      case true => finish()
      case false => initFirstQuestion()
    }
  }

  private def initFirstQuestion(): Unit = {
    (testStateActor ? CurrentQuestion).mapTo[Question]
      .foreach(curQuestion => accessUI {
        this.mainScreen.replaceQuestionForm(new QuestionForm(curQuestion))
      })
  }

  def finish(): Unit = {
    saveCurrentQuestionFormState()

    (testStateActor ? Results).mapTo[Array[Question]]
      .foreach(results => {
        accessUI {
          this.mainScreen.removeAll()
          this.mainScreen.add(new ResultForm(TestResult(results)))
        }
      })

  }

  protected[web] def prevButtonClicked(): Unit = {
    saveCurrentQuestionFormState()
    (testStateActor ? PrevQuestion).mapTo[Question]
      .foreach(prevQuestion => accessUI {
        mainScreen.replaceQuestionForm(new QuestionForm(prevQuestion))
      })
  }

  private def saveCurrentQuestionFormState(): Unit = {
    mainScreen.getCurrentAnswer.foreach(answer => {
      testStateActor ! SaveCurrentQuestionState(answer)
    })
  }

  private def accessUI(f: => Unit): Unit = {
    ui.access(() => f)
  }

  protected[web] def nextButtonClicked(): Unit = {
    saveCurrentQuestionFormState()
    (testStateActor ? NextQuestion).mapTo[Question]
      .foreach(nextQuestion => accessUI {
        mainScreen.replaceQuestionForm(new QuestionForm(nextQuestion))
      })
  }

  //todo use it
  private def restart(): Unit = {
    (testStateActor ? CurrentQuestion).mapTo[Question]
      .foreach(currentQuestion => {
        testStateActor ! Restart
        accessUI {
          this.mainScreen.removeAll()
          this.mainScreen.replaceQuestionForm(new QuestionForm(currentQuestion))
        }
      })
  }
}
