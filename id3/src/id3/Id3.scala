package id3

import dataset.{Dataset, Leaf, Node}

object Id3 {
  /**
    *
    * @param conclusion - what it is wanted to be found
    * @param data - input data
    * @return - a decision tree where nodes are represented by attributes and values from data
    *         and leafs are represented by conclusion values.
    */
  def apply(conclusion: Dataset, data: List[Dataset]): Node = {
    // only 1 attribute and the conclusion table has the same value everywhere
    if (data.length == 1 && data.head.data.distinct.length == 1) {
      solveSingleAttribute(conclusion, data.head)
    }
    else {
      val currentBestAttribute = BestAttributeFinder(conclusion, data)

      Node(currentBestAttribute._1, getNodes(currentBestAttribute) ::: getLeafs(currentBestAttribute))
    }
  }

  /**
    *
    * @param conclusion - what it is wanted to be found
    * @param data       - entry data
    * @return - a procentage of the higher value found in conclusion
    *         Used when there is one attribute left and its the got the same value on any row.
    */
  def solveSingleAttribute(conclusion: Dataset, data: Dataset): Node = {
    def chooseMajority(conclusion: Dataset): String = {
      val distinctValues = conclusion.data.distinct

      // finding out their count, kept in a tuple of (value, count)
      // so the max count can be found
      val count = distinctValues.map(e => (e, conclusion.data.count(_ == e)))

      val leafValue = count.maxBy(_._2)

      s"""${leafValue._2} / ${conclusion.data.length} ${leafValue._1}"""
    }

    Node(data.attribute,
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
  private def getLeafs(tables: (String, List[(String, List[Dataset], Dataset)])): List[Node] = {
    // filtering for tables where the conclusion's values are the same
    val toBeLeafs = tables._2.filter(e => e._3.data.distinct.length == 1)

    // creating nodes which carry the name of the attribute
    // with leafs represented by that singular value from the conclusion column
    toBeLeafs.map(e => Node(e._1, List.empty, List(Leaf(e._3.data.head))))
  }

  /**
    *
    * @param tables - after finding out the best attribute for a node,
    *               the table is split by attribute's value
    * @return - a list of nodes where the children are represented by the subtree returned by the recursive call
    */
  private def getNodes(tables: (String, List[(String, List[Dataset], Dataset)])): List[Node] = {
    // filtering all the tables where there are more possible conclusion values
    val toBeNodes = tables._2.filterNot(e => e._3.data.distinct.length == 1)

    // creating the node with the name of the attribute,
    // and where the children are represented by a recursive call holding each sub-table independently
    toBeNodes.map(e => Node(e._1, List(Id3(e._3, e._2))))
  }
}
