package ru.shaldnikita.testing.web.result

import com.vaadin.flow.component.dependency.{HtmlImport, StyleSheet}
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.data.renderer.TemplateRenderer
import ru.shaldnikita.testing.data.entities.{AnswerPair, TestResult}

import scala.collection.JavaConverters._

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
class ResultForm(result: TestResult) extends VerticalLayout {

  import com.vaadin.flow.function.ValueProvider

  val cssClassProvider: ValueProvider[AnswerPair, String] = { pair =>
    var cssClass = "final-results-grid-cell"
    if (pair.answer == pair.correctAnswer) cssClass += "correct-answer"
    else cssClass += " wrong-answer"
    cssClass
  }

  val resultGrid = new Grid[AnswerPair]()

  resultGrid.addColumn(TemplateRenderer.of("<div class$=\"[[item.class]]\">[[item.answer]]</div>")
    .withProperty("class", cssClassProvider)
    .withProperty("answer", pair => pair.answer))
    .setHeader("Выбрано")

  resultGrid.addColumn(TemplateRenderer.of("<div class$=\"[[item.class]]\">[[item.correctAnswer]]</div>")
    .withProperty("class", cssClassProvider)
    .withProperty("correctAnswer", pair => pair.correctAnswer))
    .setHeader("Верный ответ");

  resultGrid.setItems(result.results.asJavaCollection)

  val correctAnswersCount = new Label("Верных ответов: " + result.correctAnswers.size)

  add(correctAnswersCount, resultGrid)
}

