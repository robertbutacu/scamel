import dataset.{Dataset, Node}
import id3.Id3

object Main extends App {
  def prettyPrinter(node: Node, levelOfDepth: Int = 0): Unit = {
    print("\t" * levelOfDepth)
    println("Current node: " + node.attribute + "\n")

    node.leafs.foreach(l => println("\t" * (levelOfDepth + 1) + "Leaf: " + l.attribute))

    node.nodes.foreach(n => prettyPrinter(n, levelOfDepth + 1))
  }

  prettyPrinter(Id3(Dataset("Transportation",
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
