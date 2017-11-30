package id3

import java.lang.Math.log

import dataset.{BestAttribute, Dataset, Subset}

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
object BestAttributeFinder {
  type Entropy = Double
  type InformationGain = Double

  /**
    *
    * @param conclusion   - based on the trainingData, a conclusion is wanted to be reached in case of further
    *                     data entrances
    * @param trainingData - all attributes, columns
    * @return A BestAttribute for current table with current conclusion.
    */
  def apply(conclusion: Dataset, trainingData: List[Dataset]): BestAttribute = {
    val conclusionEntropy = computeEntropy(conclusion.data)

    val attributes = trainingData.map(e => (e, conclusion))

    val informationGains = attributes.map(e => ((conclusionEntropy - computeDatasetEntropy(e)) * 1000).floor / 1000)

    val bestAttributeIndex = informationGains.indexOf(informationGains.max)

    val subsets = trainingData(bestAttributeIndex).data
      .distinct
      .map { v => createSubsetFromRowValue(bestAttributeIndex, v, trainingData, conclusion) }

    //using patch to remove the best attribute column for the subset
    BestAttribute(trainingData(bestAttributeIndex).attribute,
      subsets.map(t => Subset(t._1(bestAttributeIndex).data.head, //attribute name - head cause they all the same
        t._1.patch(bestAttributeIndex, Nil, 1), //data
        t._2))) //conclusion
  }


  /** log(base e) x/log(base e) 2
    * Scala/Java doesn't have anything implemented for log of custom base ( but 10 and e )
    * manual conversion needed
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

    val entropy = probabilities.foldRight(0.0)((curr, acc) => acc - curr._2 * (log(curr._2) / log(2)))

    (entropy * 10000).floor / 10000
  }


  /** Why use zipWithIndex => a value from a given dataset will need to be filtered against the value
    * it is wanted to create the subset by. To do this, the index is needed - simple as that.
    *
    * @param columnIndex  - index of column which will be checked for equality, needed to filter the data
    * @param rowValue     - the value of the row which will be used to filter the data
    * @param trainingData - input dataset
    * @param conclusion   - the conclusion for an input data
    * @return a new table ( which is represented as a list of training data ), and its inferred conclusion,
    *         where each value of the column matching columnIndex is equal to rowValue
    */
  def createSubsetFromRowValue(columnIndex: Int,
                               rowValue: String,
                               trainingData: List[Dataset],
                               conclusion: Dataset): (List[Dataset], Dataset) = {
    (trainingData
      .map { e =>
        Dataset(e.attribute,
          e.data
            .zipWithIndex
            //value of the row in the columnIndex is equal to the rowValue
            .withFilter(r => trainingData(columnIndex).data(r._2) == rowValue)
            .map(r => r._1))
      },
      Dataset(conclusion.attribute,
        conclusion.data
          .zipWithIndex
          .withFilter(r => trainingData(columnIndex).data(r._2) == rowValue)
          .map(r => r._1)
      )
    )
  }


  /**
    * In order to compute information gain, it is needed for subsets to be created for each unique dataset value
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
    * @param trainingData - a sub-table
    * @return a list of subsets where e._1 is unique in any of the elements
    */
  private def splitIntoSubsets(trainingData: List[(String, String)]): List[List[(String, String)]] = {
    val (packed, next) = trainingData.span(_._1 == trainingData.head._1)
    if (next.isEmpty) List(packed)
    else packed :: splitIntoSubsets(next)
  }


  /**
    *
    * @param trainingData - the subset for which an entropy is required
    * @return the entropy for an attribute, which will be later require to compute information gain.
    */
  private def computeDatasetEntropy(trainingData: (Dataset, Dataset)): Entropy = {
    val rows = trainingData._1.data.indices.map(e => (trainingData._1.data(e), trainingData._2.data(e))).toList

    val tables = splitIntoSubsets(rows.sortWith((e1, e2) => e1._1 < e2._1))

    tables.map(t => entropyForSubset(t, rows.length)).sum
  }


  /**
    * Represented by - (numberOfElementsInSubtable/numberOfElementsInTable) * Entropy(Attribute)
    * The minus will be added by the apply function.
    */
  private def entropyForSubset(subset: List[(String, String)], tableSize: Int): Entropy =
    subset.length.toDouble / tableSize.toDouble * computeEntropy(subset.map(e => e._2))
}
