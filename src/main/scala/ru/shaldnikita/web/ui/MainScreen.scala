package ru.shaldnikita.web.ui

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.orderedlayout.{HorizontalLayout, VerticalLayout}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
@Component
@Scope("prototype")
class MainScreen(@Autowired mainScreenPresenter: MainScreenPresenter) extends VerticalLayout {
  mainScreenPresenter.init(this)

  protected[ui] var currentQuestionForm: QuestionForm = _

  private val prevButton = new Button("Назад")
  prevButton.addClickListener(_ => mainScreenPresenter.prevButtonClicked())
  private val nextButton = new Button("Вперед")
  nextButton.addClickListener(_ => mainScreenPresenter.nextButtonClicked())
  private val finishButton = new Button("Завершить")
  finishButton.addClickListener(_ => mainScreenPresenter.finish())
  private val buttons = new HorizontalLayout(prevButton, nextButton)

  if (currentQuestionForm != null)
    add(currentQuestionForm, new VerticalLayout(buttons, finishButton))

  protected[ui] def replaceQuestionForm(newQuestionForm: QuestionForm) = {
    if (currentQuestionForm != null)
      replace(currentQuestionForm, newQuestionForm)
    currentQuestionForm = newQuestionForm
  }

  protected[ui] def getCurrentQuestionForm(): Option[QuestionForm] ={
    Option(currentQuestionForm)
  }
}
