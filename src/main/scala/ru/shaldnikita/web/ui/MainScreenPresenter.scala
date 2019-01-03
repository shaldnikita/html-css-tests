package ru.shaldnikita.web.ui

import com.vaadin.flow.spring.annotation.VaadinSessionScope
import org.springframework.stereotype.Component
import ru.shaldnikita.web.DataLoader

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
@Component
@VaadinSessionScope
class MainScreenPresenter {
  private var mainScreen: MainScreen = _
  private val questions = DataLoader.getRandom10Questions.map(question =>
    (question, Option.empty[String])).toArray
  private var curIndex = 0
  private var finished = false

  def finish() = {
    saveCurrentQuestionFormState()
    this.mainScreen.removeAll()
    this.mainScreen.add(new ResultForm(
      questions.filter(_._2.isDefined).map(qa => (qa._1, qa._2.get))
    ))
    finished = true
  }

  protected[ui] def prevButtonClicked(): Unit = {
    saveCurrentQuestionFormState()
    val newIndex = if (curIndex == 0) 9 else curIndex - 1
    replaceQuestion(newIndex)
  }

  protected[ui] def nextButtonClicked(): Unit = {
    saveCurrentQuestionFormState()
    val newIndex = if (curIndex == 9) 0 else curIndex + 1
    replaceQuestion(newIndex)
  }

  protected[ui] def init(mainScreen: MainScreen): Unit = {
    this.mainScreen = mainScreen
    if(finished){
      finish()
      return
    }
    this.mainScreen.replaceQuestionForm(new QuestionForm(questions(curIndex)._1))
  }

  private def saveCurrentQuestionFormState(): Unit = {
    mainScreen.getCurrentQuestionForm().foreach(form => {
      val curQuestion = questions(curIndex)._1
      questions(curIndex) = (curQuestion, form.currentAnswer)
    })
  }

  private def replaceQuestion(index: Int): Unit = {
    val newQuestion = questions(index)
    mainScreen.replaceQuestionForm(new QuestionForm(newQuestion._1, newQuestion._2))
    curIndex = index
  }

}
