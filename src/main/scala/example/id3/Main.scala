package example.id3

import algorithms.id3.{Id3, Id3Classifier}
import data.id3.Dataset

object Main extends App {
  val result = Id3(
    Dataset[String, String]("Transportation",
      List[String]("Bus", "Bus", "Train", "Bus", "Bus", "Train", "Train", "Car", "Car", "Car")),
    List(
      Dataset[String, String]("Gender",
        List[String]("Male", "Male", "Female", "Female", "Male", "Male", "Female", "Female", "Male", "Female")),
      Dataset[String, String]("Car ownership",
        List[String]("0", "1", "1", "0", "1", "0", "1", "1", "2", "2")),
      Dataset[String, String]("Travel Cost",
        List[String]("Cheap", "Cheap", "Cheap", "Cheap", "Cheap", "Standard", "Standard", "Expensive", "Expensive", "Expensive")),
      Dataset[String, String]("Income Level",
        List[String]("Low", "Medium", "Medium", "Low", "Medium", "Medium", "Medium", "High", "Medium", "High")))
  )

  //println(result)
  result.foreach(n => Id3.prettyPrinter(n))

  println("\n\nClassifying")

  result.foreach(n => println(Id3Classifier.classify(n, Map(
    "Gender" -> "Male",
    "Car ownership" -> "0",
    "Income Level" -> "High",
    "Travel Cost" -> "Cheap"
  ))))
}
