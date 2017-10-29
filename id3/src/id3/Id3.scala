package id3

import dataset.{Dataset, Node}
import Math.log

object Id3 {
  /**
    * Compute the entropy for each attribute
    * Split the set into subsets using the attribute for each entropy is minimum ( <=> information gain is maximal )
    * Make a decision Tree containing that attribute
    * Recurse on subsets using remaining attributes
    * Entropy(S) = -p(I)log2(p(I))
    * InformationGain(S, A) = Entropy(S) - ((|Sv| \ |S|) * Entropy(Sv))
    * Sv = subset of S for which attribute A has value v
    * |Sv| = number of elements in Sv
    * |S|  = number of elements in S
    */

  def apply(conclusion: Dataset, data: List[Dataset]): Node = {
    val conclusionEntropy = computeEntropy(conclusion)
    println(conclusionEntropy)
    Node("result")
  }

  private def computeEntropy(input: Dataset): Double = {
    val probabilities = input.data
      .map(e => (e, input.data.count(b => b == e).toDouble / input.data.length.toDouble)).toSet

    val entropy = probabilities.foldRight(0.0)((curr, acc) =>
      // log(base e) x/log(base e) 2
      // Scala/Java doesn't have anything implemented for log of any base
      // manual conversion needed
      acc - curr._2 * (log(curr._2) / log(2))
    )

    (entropy * 10000).floor / 10000
  }
}
