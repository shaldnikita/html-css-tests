package ru.shaldnikita.web.ui

import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import ru.shaldnikita.web.pojo.Question

import scala.collection.JavaConverters._

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
class ResultForm(results: Array[(Question, String)]) extends VerticalLayout {

  val answers = results.map(question => (question._1.correctAnswer, question._2)).toList.asJava
  val resultGrid: Grid[(String, String)] = new Grid[(String, String)]()

  resultGrid.addColumn(answers => answers._1).setHeader("Выбрано")
  resultGrid.addColumn(answers => answers._2).setHeader("Верный ответ")
  resultGrid.setItems(answers)

  val correctAnswers = answers.asScala.filter(i => i._1 == i._2)
  val correctAnswersCount = new Label("Верных ответов: " + correctAnswers.size)

  add(correctAnswersCount, resultGrid)
}
