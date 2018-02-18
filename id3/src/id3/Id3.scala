package id3

import dataset.data.{BestAttribute, Dataset}
import dataset.data.tree.{Leaf, Node}

object Id3 {
  /**
    *
    * @param conclusion   - what it is wanted to be found
    * @param trainingData - input data
    * @return - a decision tree where nodes are represented by attributes and values from data
    *         and leafs are represented by conclusion values.
    */
  def apply[A <: Ordering[A], B](conclusion: Dataset[A, B], trainingData: List[Dataset[A, B]]): Node[A, B] = {
    // only 1 attribute and the conclusion table has the same value everywhere
    if (trainingData.lengthCompare(1) == 0 && trainingData.head.data.distinct.lengthCompare(1) == 0) {
      solveSingleAttribute(conclusion, trainingData.head)
    }
    else {
      val currentBestAttribute = BestAttributeFinder(conclusion, trainingData)

      Node(currentBestAttribute.attribute, getNodes(currentBestAttribute) ::: getLeafs(currentBestAttribute))
    }
  }

  /**
    *
    * @param conclusion         - what it is wanted to be found
    * @param trainingDataColumn - entry data
    * @return - a procentage of the higher value found in conclusion
    *         Used when there is one attribute left and its the got the same value on any row.
    */
  def solveSingleAttribute[A <: Ordering[A], B](conclusion: Dataset[A, B], trainingDataColumn: Dataset[A, B]): Node[A, B] = {
    def chooseMajority(conclusion: Dataset[A, B]): A = {
      val distinctValues = conclusion.data.distinct

      // finding out their count, kept in a tuple of (value, count)
      // so the max count can be found
      val count = distinctValues.map(e => (e, conclusion.data.count(_ == e)))

      val leafValue = count.maxBy(_._2)

      leafValue._1
    }

    Node(trainingDataColumn.attribute,
      List.empty,
      List(Leaf(chooseMajority(conclusion))))
  }

  /**
    *
    * @param tables - after finding out the best attribute for a node,
    *               the table is split by attribute's value
    * @return - a list of nodes where the leafs are represented by all the subtables
    *         where the conclusion rows are the same.
    */
  private def getLeafs[A <: Ordering[A], B](tables: BestAttribute[A, B]): List[Node[A, B]] = {
    // filtering for tables where the conclusion's values are the same
    val toBeLeafs = tables.subsets.filter(e => e.conclusion.data.distinct.lengthCompare(1) == 0)

    // creating nodes which carry the name of the attribute
    // with leafs represented by that singular value from the conclusion column
    toBeLeafs.map(e => Node(e.attribute, List.empty, List(Leaf(e.conclusion.data.head))))
  }

  /**
    *
    * @param tables - after finding out the best attribute for a node,
    *               the table is split by attribute's value
    * @return - a list of nodes where the children are represented by the subtree returned by the recursive call
    */
  private def getNodes[A <: Ordering[A], B](tables: BestAttribute[A, B]): List[Node[A, B]] = {
    // filtering all the tables where there are more possible conclusion values
    val toBeNodes = tables.subsets.filterNot(e => e.conclusion.data.distinct.lengthCompare(1) == 0)

    // creating the node with the name of the attribute,
    // and where the children are represented by a recursive call holding each sub-table independently
    toBeNodes.map(e => Node(e.attribute, List(Id3(e.conclusion, e.table))))
  }
}
