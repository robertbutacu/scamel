package id3

import dataset.{Dataset, Node}
import Math.log

object Id3 {
  type Entropy = Double

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

    val branches = data.map(e => (e, conclusion))

    branches.foreach(e => computeProbabilities(e))
    //println(branches)
    Node("result")
  }

  private def splitIntoTables(table: List[(String, String)]): List[List[(String, String)]] = {
    val (packed, next) = table.span(_._1 == table.head._1)
    if(next.isEmpty) List(packed)
    else packed :: splitIntoTables(next)

  }

  private def computeProbabilities(dataset: (Dataset, Dataset)): Entropy = {
    val uniqueFields = dataset._1.data.toSet

    var rows = dataset._1.data.indices.map(e => (dataset._1.data(e), dataset._2.data(e))).toList

    rows = rows.sortWith((e1, e2) => e1._1 < e2._1)
    println(splitIntoTables(rows))

    0.0
  }

  private def computeEntropy(input: Dataset): Entropy = {
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
