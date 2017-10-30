package id3

import dataset.{Dataset, Node}
import Math.log

object BestAttributeFinder {
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

  def apply(conclusion: Dataset, data: List[Dataset]): List[(List[Dataset], Dataset)] = {
    val conclusionEntropy = computeEntropy(conclusion.data)

    val branches = data.map(e => (e, conclusion))

    val informationGains = branches.map(e => ((conclusionEntropy - computeDatasetEntropy(e)) * 1000).floor / 1000)

    val bestAttributeIndex = informationGains.indexOf(informationGains.max)

    val subtables = data(bestAttributeIndex).data
      .distinct
      .map{v => createSubtableFromRow(bestAttributeIndex, v, data, conclusion)}

    println(subtables)
    List.empty
  }

  /** Why use zipWithIndex => a value from a given dataset will need to be filtered against the value
    *   it is wanted to create the sub-table by. To do this, the index is needed - simple as that.
    *
    * @param columnIndex - index of column which will be checked for equality, needed to filter the data
    * @param rowValue - the value of the row which will be used to filter the data
    * @param dataset - input dataset
    * @param conclusion - the conclusion for an input data
    * @return a new table ( which is represented as a list of datasets ), and its inferred conclusion,
    *         where each value of the column matching columnIndex is equal to rowValue
    */
  def createSubtableFromRow(columnIndex: Int,
                            rowValue: String,
                            dataset: List[Dataset],
                            conclusion: Dataset): (List[Dataset], Dataset) = {
    (dataset
      .map { e =>
        Dataset(e.attribute,
          e.data
            .zipWithIndex
            //value of the row in the columnIndex is equal to the rowValue
            .filter(r => dataset(columnIndex).data(r._2) == rowValue)
            .map(r => r._1))
      },
      Dataset(conclusion.attribute,
        conclusion.data
          .zipWithIndex
          .filter(r => dataset(columnIndex).data(r._2) == rowValue)
          .map(r => r._1)
      )
    )
  }


  /**
    * In order to compute information gain, it is needed for sub-tables to be created for each unique dataset value
    * Example:
    * Female Bus
    * Female Bus
    * Man    Train
    * Man    Car
    *
    * => (
    * Female Bus
    * Female Bus
    * ),
    * (
    * Man    Train
    * Man    Car
    * )
    * This way, it is easy to compute the entropy for each value and add it up to a information gain.
    * @param table - a sub-table
    * @return a list of tables where e._1 is unique in any of the elements
    */
  private def splitIntoTables(table: List[(String, String)]): List[List[(String, String)]] = {
    val (packed, next) = table.span(_._1 == table.head._1)
    if (next.isEmpty) List(packed)
    else packed :: splitIntoTables(next)
  }


  /**
    *
    * @param dataset - the sub-table for which an entropy is required
    * @return the entropy for an attribute, which will be later require to compute information gain.
    */
  private def computeDatasetEntropy(dataset: (Dataset, Dataset)): Entropy = {
    val rows = dataset._1.data.indices.map(e => (dataset._1.data(e), dataset._2.data(e))).toList

    val tables = splitIntoTables(rows.sortWith((e1, e2) => e1._1 < e2._1))

    tables.map(t => entropyForSubTable(t, rows.length)).sum
  }


  /**
    * Represented by - (numberOfElementsInSub-table/numberOfElementsInTable) * Entropy(Attribute)
    * The minus will be added by the apply function.
    */
  private def entropyForSubTable(subTable: List[(String, String)], tableSize: Int): Entropy =
    subTable.length.toDouble / tableSize.toDouble * computeEntropy(subTable.map(e => e._2))


  /**
    *
    * Represented by Sum( - P(i) * log2(P(i)) ).
    * Where P(i) => probability of i
    * i    => each unique data entry for the attribute
    *
    * @return - entropy rounded to 4 decimals
    */
  private def computeEntropy(input: List[String]): Entropy = {
    val probabilities = input
      .map(e => (e, input.count(b => b == e).toDouble / input.length.toDouble)).toSet

    val entropy = probabilities.foldRight(0.0)((curr, acc) =>
      // log(base e) x/log(base e) 2
      // Scala/Java doesn't have anything implemented for log of custom base ( but 10 and e )
      // manual conversion needed
      acc - curr._2 * (log(curr._2) / log(2))
    )

    (entropy * 10000).floor / 10000
  }
}
