package ru.shaldnikita.testing.web.mainscreen

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.orderedlayout.{HorizontalLayout, VerticalLayout}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import ru.shaldnikita.testing.web.question.QuestionForm

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
@Component
@Scope("prototype")
class MainScreen(@Autowired mainScreenPresenter: MainScreenPresenter) extends VerticalLayout {
  mainScreenPresenter.init(this)

  protected[web] var currentQuestionForm: QuestionForm = _

  private val prevButton = new Button("Назад")
  prevButton.addClickListener(_ => mainScreenPresenter.prevButtonClicked())
  private val nextButton = new Button("Вперед")
  nextButton.addClickListener(_ => mainScreenPresenter.nextButtonClicked())
  private val finishButton = new Button("Завершить")
  finishButton.addClickListener(_ => mainScreenPresenter.finish())
  private val buttons = new HorizontalLayout(prevButton, nextButton)

  if (currentQuestionForm != null)
    add(currentQuestionForm, new VerticalLayout(buttons, finishButton))


  protected[web] def replaceQuestionForm(newQuestionForm: QuestionForm): Unit = {
    if (currentQuestionForm != null)
      replace(currentQuestionForm, newQuestionForm)
    currentQuestionForm = newQuestionForm
  }

  protected[web] def getCurrentQuestionForm: Option[QuestionForm] = {
    Option(currentQuestionForm)
  }
}
