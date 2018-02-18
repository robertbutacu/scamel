package id3

import dataset.data.tree.{Connection, Leaf, Node}
import dataset.data.{BestAttribute, Dataset}

object Id3 {
  def apply[A: Ordering, B](conclusion: Dataset[A, B], trainingData: List[Dataset[A, B]]): Node[A, B] = {
    if (trainingData.lengthCompare(1) == 0 && trainingData.head.data.distinct.lengthCompare(1) == 0) {
      solveSingleAttribute(conclusion, trainingData.head)
    }
    else {
      val currentBestAttribute = BestAttributeFinder(conclusion, trainingData)

      Node(currentBestAttribute.attribute, getNodes(currentBestAttribute), getLeafs(currentBestAttribute))
    }
  }

  /**
    *
    * @param conclusion   - what it is wanted to be found
    * @param trainingData - input data
    * @return - a decision tree where nodes are represented by attributes and values from data
    *         and leafs are represented by conclusion values.
    */
  def go[A: Ordering, B](conclusion: Dataset[A, B], trainingData: List[Dataset[A, B]]): List[Connection[A, B]] = {
    // only 1 attribute and the conclusion table has the same value everywhere
    val currentBestAttribute = BestAttributeFinder(conclusion, trainingData)

    currentBestAttribute.subsets.map(e =>
      Connection(
        e.attribute,
        Node(currentBestAttribute.attribute,
          getNodes(currentBestAttribute),
          getLeafs(currentBestAttribute))
      )
    )
  }

  /**
    *
    * @param conclusion         - what it is wanted to be found
    * @param trainingDataColumn - entry data
    * @return - a procentage of the higher value found in conclusion
    *         Used when there is one attribute left and its the got the same value on any row.
    */
  def solveSingleAttribute[A: Ordering, B](conclusion: Dataset[A, B], trainingDataColumn: Dataset[A, B]): Node[A, B] = {
    def chooseMajority(): A = {
      val distinctValues = conclusion.data.distinct

      // finding out their count, kept in a tuple of (value, count)
      // so the max count can be found
      val count = distinctValues.map(e => (e, conclusion.data.count(_ == e)))

      val leafValue = count.maxBy(_._2)

      leafValue._1
    }

    new Node(trainingDataColumn.attribute,
      List.empty,
      List((chooseMajority(), Leaf(chooseMajority()))))
  }

  /**
    *
    * @param tables - after finding out the best attribute for a node,
    *               the table is split by attribute's value
    * @return - a list of nodes where the leafs are represented by all the subtables
    *         where the conclusion rows are the same.
    */
  private def getLeafs[A: Ordering, B](tables: BestAttribute[A, B]): List[(A, Leaf[A])] = {
    // filtering for tables where the conclusion's values are the same
    val toBeLeafs = tables.subsets.filter(e => e.conclusion.data.distinct.lengthCompare(1) == 0)

    // creating nodes which carry the name of the attribute
    // with leafs represented by that singular value from the conclusion column
    toBeLeafs.map(e => (e.attribute, Leaf(e.conclusion.data.head)))
  }

  /**
    *
    * @param tables - after finding out the best attribute for a node,
    *               the table is split by attribute's value
    * @return - a list of nodes where the children are represented by the subtree returned by the recursive call
    */
  private def getNodes[A: Ordering, B](tables: BestAttribute[A, B]): List[Connection[A, B]] = {
    // filtering all the tables where there are more possible conclusion values
    val toBeNodes = tables.subsets.filterNot(e => e.conclusion.data.distinct.lengthCompare(1) == 0)

    toBeNodes.foreach(e => println(e.attribute))
    // creating the node with the name of the attribute,
    // and where the children are represented by a recursive call holding each sub-table independently
    toBeNodes.map(e => Connection(e.attribute, Node(tables.attribute, Id3.go(e.conclusion, e.table))))
  }
}
