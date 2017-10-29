package id3

import dataset.{Dataset, Node}

object Id3 {
  /**
    * Compute the entropy for each attribute
    * Split the set into subsets using the attribute for each entropy is minimum ( <=> information gain is maximal )
    * Make a decision Tree containing that attribute
    * Recurse on subsets using remaining attributes
    *   Entropy(S) = -p(I)log2(p(I))
    *   InformationGain(S, A) = Entropy(S) - ((|Sv| \ |S|) * Entropy(Sv))
    *   Sv = subset of S for which attribute A has value v
    *   |Sv| = number of elements in Sv
    *   |S|  = number of elements in S
    */

  def apply(conclusion: Dataset, data: List[Dataset]): Node = {
    println(conclusion)
    println(data)
    Node("result")
  }
}
