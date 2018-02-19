package id3.algorithm

import id3.data.tree.{Leaf, LeafConnection, Node, NodeConnection}
import id3.data.{BestAttribute, Dataset, Subset}

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
    * @param conclusion         - what it is wanted to be found
    * @param trainingDataColumn - entry data
    * @return - a procentage of the higher value found in conclusion
    *         Used when there is one attribute left and its the got the same value on any row.
    */
  def solveSingleAttribute[A: Ordering, B](conclusion: Dataset[A, B],
                                           trainingDataColumn: Dataset[A, B]): Node[A, B] = {
    def chooseMajority(): (A, Double) = {
      val distinctValues = conclusion.data.distinct

      // finding out their count, kept in a tuple of (value, count)
      // so the max count can be found
      val count = distinctValues.map(e => (e, conclusion.data.count(_ == e)))

      val leafValue = count.maxBy(_._2)

      (leafValue._1, leafValue._2 / count.length)
    }

    val majority = chooseMajority()

    new Node(trainingDataColumn.attribute,
      List.empty,
      List(LeafConnection(majority._1, Leaf(majority._1), majority._2)))
  }

  /**
    *
    * @param tables - after finding out the best attribute for a node,
    *               the table is split by attribute's value
    * @return - a list of nodes where the leafs are represented by all the subtables
    *         where the conclusion rows are the same.
    */
  protected[id3] def getLeafs[A: Ordering, B](tables: BestAttribute[A, B]): List[LeafConnection[A]] = {
    // filtering for tables where the conclusion's values are the same
    val toBeLeafs = tables.subsets.filter(e => Subset.isUniqueConclusion(e))

    // creating nodes which carry the name of the attribute
    // with leafs represented by that singular value from the conclusion column
    toBeLeafs.map(e => LeafConnection(e))
  }

  /**
    *
    * @param tables - after finding out the best attribute for a node,
    *               the table is split by attribute's value
    * @return - a list of nodes where the children are represented by the subtree returned by the recursive call
    */
  protected[id3] def getNodes[A: Ordering, B](tables: BestAttribute[A, B]): List[NodeConnection[A, B]] = {
    // filtering all the tables where there are more possible conclusion values
    val toBeNodes = tables.subsets.filterNot(e => Dataset.isUniqueData(e.conclusion))

    // creating the node with the name of the attribute,
    // and where the children are represented by a recursive call holding each sub-table independently
    toBeNodes.map { e =>
      val nextBestAttribute = BestAttributeFinder(e.conclusion, e.table)
      NodeConnection(e.attribute, Node(nextBestAttribute))
    }
  }
}
