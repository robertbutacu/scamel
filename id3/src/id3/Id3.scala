package id3

import dataset.{Dataset, Node}

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
    val currentBestAttribute = BestAttributeFinder(conclusion, data)

    println(currentBestAttribute)

    Node("test")
  }

}
