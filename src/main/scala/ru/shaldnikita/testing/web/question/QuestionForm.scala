package ru.shaldnikita.testing.web.question

import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.radiobutton.RadioButtonGroup
import ru.shaldnikita.testing.domain.entities.Question

import scala.collection.JavaConverters._

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
class QuestionForm(question: Question, selectedAnswer: Option[String] = None)
  extends VerticalLayout {

  private val questionLabel = new Label(question.question)

  private val answersRadioButton = new RadioButtonGroup[String]()
  answersRadioButton.addValueChangeListener(_ => {
    answersRadioButton.setReadOnly(true)
  })
  answersRadioButton.setItems(question.answers.asJava)
  selectedAnswer.foreach(_ => answersRadioButton.setValue(selectedAnswer.orNull))

  add(questionLabel, answersRadioButton)

  protected[web] def currentAnswer: Option[String] = {
    Option(answersRadioButton.getValue)
  }
}
