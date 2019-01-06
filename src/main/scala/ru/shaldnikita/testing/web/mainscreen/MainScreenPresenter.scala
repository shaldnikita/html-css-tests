package ru.shaldnikita.testing.web.mainscreen

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ru.shaldnikita.testing.domain.entities.TestResult
import ru.shaldnikita.testing.web.State
import ru.shaldnikita.testing.web.question.QuestionForm
import ru.shaldnikita.testing.web.result.ResultForm

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
@Component
@Scope("prototype")
class MainScreenPresenter(@Autowired private val state: State) {
  private var mainScreen: MainScreen = _

  def init(mainScreen: MainScreen): Unit = {
    this.mainScreen = mainScreen
    if (state.finished) {
      finish()
      return
    }
    this.mainScreen.replaceQuestionForm(new QuestionForm(state.currentQuestion))
  }

  def finish(): Unit = {
    saveCurrentQuestionFormState()
    this.mainScreen.removeAll()
    this.mainScreen.add(new ResultForm(TestResult(state.results)))
    state.finish()
  }

  private def saveCurrentQuestionFormState(): Unit = {
    mainScreen.getCurrentQuestionForm.foreach(form => {
      state.saveCurrentQuestion(form.currentAnswer)
    })
  }

  def restart(): Unit = {
    state.reset()
    this.mainScreen.removeAll()
    this.mainScreen.replaceQuestionForm(new QuestionForm(state.currentQuestion))
  }

  protected[web] def prevButtonClicked(): Unit = {
    saveCurrentQuestionFormState()
    val prevQuestion = state.prevQuestion
    mainScreen.replaceQuestionForm(new QuestionForm(prevQuestion._1, prevQuestion._2))
  }

  protected[web] def nextButtonClicked(): Unit = {
    saveCurrentQuestionFormState()
    val nextQuestion = state.nextQuestion
    mainScreen.replaceQuestionForm(new QuestionForm(nextQuestion._1, nextQuestion._2))
  }

}
