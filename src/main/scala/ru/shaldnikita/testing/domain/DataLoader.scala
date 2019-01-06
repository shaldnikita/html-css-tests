package ru.shaldnikita.testing.domain

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import ru.shaldnikita.testing.domain.entities.{EncodedQuestion, QuestionData}
import spray.json.{DefaultJsonProtocol, _}

import scala.io.Source._
import scala.util.Random


/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
object DataLoader extends QuestionFormatSupport {

  private val questionsJson = fromInputStream(
    DataLoader.getClass.getClassLoader.getResourceAsStream("output.txt"), "UTF-8").mkString
  private val questions = questionsJson.parseJson.convertTo[List[QuestionData]]

  def getRandom10Questions: Seq[QuestionData] = {
    var numbers: Set[Int] = Set.empty
    while (numbers.size < 10) {
      numbers = numbers + Random.nextInt(97)
    }
    numbers.map(i => questions(i)).toSeq
  }
}

trait QuestionFormatSupport extends SprayJsonSupport with DefaultJsonProtocol {
  private val questionApply: (String, String, List[String]) => QuestionData = EncodedQuestion.apply
  implicit val questionFormat: RootJsonFormat[QuestionData] = jsonFormat3(questionApply)
}