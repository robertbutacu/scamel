import dataset.data.Dataset
import dataset.data.tree.Node
import id3.Id3

object Main extends App {
  def prettyPrinter[A: Ordering, B](node: Node[A, B], levelOfDepth: Int = 0): Unit = {
    println(node.attribute)

    node.leafs.foreach { l =>
      println("\t" * (levelOfDepth + 1) + "[Leaf] " + l.arc + " arc to " + l.to + " probability: " + l.probability)
    }

    node.nodes.foreach {
      n => print("\t" * (levelOfDepth + 1) + "[Node] " + n.arc + " arc to "); prettyPrinter(n.to, levelOfDepth + 1)
    }
  }

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
  prettyPrinter(result)
}
