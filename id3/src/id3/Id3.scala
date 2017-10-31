package id3

import dataset.{Dataset, Leaf, Node, Tree}

object Id3 {
  /**
    * There will be a function which, for a given Dataset entry, will return a root node which will represent
    * the decision tree.
    * The function will call the apply function currently implemented which returns a node,
    * and given that node , it will call itself on all the neighbors, if any, until the dataset is empty
    * or wtv the stop condition is.
    * EDITED: the apply function ( will replace it with a name), will actually return a list of tables,
    * and the above function will deal with creating the node.
    */
  def apply(conclusion: Dataset, data: List[Dataset]): Node = {
    if(data.length == 1){
      solveSingleAttribute(conclusion, data.head)
    }
    else{
      val currentBestAttribute = BestAttributeFinder(conclusion, data)

      Node(currentBestAttribute._1, getNodes(currentBestAttribute) ::: getLeafs(currentBestAttribute))
    }
  }

  private def solveSingleAttribute(conclusion: Dataset, data: Dataset): Node = {
    def chooseMajority(conclusion: Dataset): String = {
      val distinctValues = conclusion.data.distinct

      val count = distinctValues.map(e => (e, conclusion.data.count(_ == e)))

      count.maxBy(e => e._2)._1
    }

    Node(data.attribute,
      List.empty,
      List(Leaf(chooseMajority(conclusion))))
  }

  private def getLeafs(tables: (String, List[(String, List[Dataset], Dataset)])): List[Node] = {
    val leafs = tables._2.filter(e => e._3.data.distinct.length == 1)

    leafs.map(e => Node(e._1, List.empty, List(Leaf(e._3.data.head))))
  }

  private def getNodes(tables: (String, List[(String, List[Dataset], Dataset)])): List[Node] = {
    val nodes = tables._2.filterNot(e => e._3.data.distinct.length == 1)

    nodes.map(e => Node(e._1, List(Id3(e._3, e._2))))
  }
}
