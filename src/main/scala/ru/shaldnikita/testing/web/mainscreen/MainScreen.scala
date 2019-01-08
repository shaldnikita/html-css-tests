package ru.shaldnikita.testing.web.mainscreen

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.orderedlayout.{HorizontalLayout, VerticalLayout}
import javax.annotation.PostConstruct
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

  private var currentQuestionForm: QuestionForm = _

  private val prevButton = new Button("Назад")
  prevButton.addClickListener(_ => mainScreenPresenter.prevButtonClicked())

  private val nextButton = new Button("Вперед")
  nextButton.addClickListener(_ => mainScreenPresenter.nextButtonClicked())

  private val finishButton = new Button("Завершить")
  finishButton.addClickListener(_ => mainScreenPresenter.finish())

  private val buttons = new HorizontalLayout(prevButton, nextButton)

  add(new VerticalLayout(buttons, finishButton))
  if (currentQuestionForm != null)
    addComponentAsFirst(currentQuestionForm)

  @PostConstruct
  def afterPropertiesSet(): Unit = {
    mainScreenPresenter.init(this, UI.getCurrent)
  }

  protected[web] def replaceQuestionForm(newQuestionForm: QuestionForm): Unit = {
    if (currentQuestionForm != null)
      replace(currentQuestionForm, newQuestionForm)
    else
      addComponentAsFirst(newQuestionForm)
    currentQuestionForm = newQuestionForm
  }

  protected[web] def getCurrentAnswer: Option[String] = {
    Option(currentQuestionForm).flatMap(_.currentAnswer)
  }
}
