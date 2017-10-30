import dataset.Dataset
import id3.BestAttributeFinder

object Main extends App {
  println(BestAttributeFinder(Dataset("Transportation",
    List("Bus", "Bus", "Train", "Bus", "Bus", "Train", "Train", "Car", "Car", "Car")),
    List(Dataset("Gender",
      List("Male", "Male", "Female", "Female", "Male", "Male", "Female", "Female", "Male", "Female")),
      Dataset("Car ownership",
        List("0", "1", "1", "0", "1", "0", "1", "1", "2", "2")),
      Dataset("Travel Cost",
        List("Cheap", "Cheap", "Cheap", "Cheap", "Cheap", "Standard", "Standard", "Expensive", "Expensive", "Expensive")),
      Dataset("Income Level",
        List("Low", "Medium", "Medium", "Low", "Medium", "Medium", "Medium", "High", "Medium", "High")))
  ))
}
