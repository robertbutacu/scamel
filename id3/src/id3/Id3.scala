package id3

import dataset.{Dataset, Node}
import Math.log

object Id3 {
  type Entropy = Double
  type InformationGain = Double

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
    val conclusionEntropy = computeEntropy(conclusion.data)

    println(conclusionEntropy)
    val branches = data.map(e => (e, conclusion))

    val informationGains = branches.map(e => computeDatasetEntropy(e))
    println(informationGains)
    Node("result")
  }

  private def splitIntoTables(table: List[(String, String)]): List[List[(String, String)]] = {
    val (packed, next) = table.span(_._1 == table.head._1)
    if (next.isEmpty) List(packed)
    else packed :: splitIntoTables(next)
  }

  private def computeDatasetEntropy(dataset: (Dataset, Dataset)): Entropy = {
    val rows = dataset._1.data.indices.map(e => (dataset._1.data(e), dataset._2.data(e))).toList

    val tables = splitIntoTables(rows.sortWith((e1, e2) => e1._1 < e2._1))

    tables.map(t => entropyForSubTable(t, rows.length)).sum
  }

  private def entropyForSubTable(subTable: List[(String, String)], tableSize: Int): Entropy =
    (subTable.length - 1).toDouble / tableSize.toDouble * computeEntropy(subTable.map(e => e._2))

  private def computeEntropy(input: List[String]): Entropy = {
    val probabilities = input
      .map(e => (e, input.count(b => b == e).toDouble / input.length.toDouble)).toSet

    val entropy = probabilities.foldRight(0.0)((curr, acc) =>
      // log(base e) x/log(base e) 2
      // Scala/Java doesn't have anything implemented for log of any base
      // manual conversion needed
      acc - curr._2 * (log(curr._2) / log(2))
    )

    (entropy * 10000).floor / 10000
  }
}
