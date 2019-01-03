package ru.shaldnikita.web


import org.apache.commons.codec.binary.Base64
import ru.shaldnikita.web.pojo.Question
import spray.json.DefaultJsonProtocol._
import spray.json._

import scala.io.Source._
import scala.util.Random

/**
  * @author n.shaldenkov on 03.01.2019
  *
  */
object DataLoader {

  private implicit val questionFormat = jsonFormat3(Question)
  private val questionsJson = fromInputStream(
    DataLoader.getClass.getClassLoader.getResourceAsStream("output.txt"),"UTF-8").mkString

  //TODO move decoding to case class
  private val questions = questionsJson.parseJson.convertTo[List[Question]]
   questions .foreach(question => question.correctAnswer = new String(
     Base64.decodeBase64(question.correctAnswer)))

  def getRandom10Questions: Seq[Question] = {
    var numbers: Set[Int] = Set.empty
    while (numbers.size < 10) {
      numbers = numbers + Random.nextInt(97)
    }
    numbers.map(i => questions(i)).toSeq
  }
}
