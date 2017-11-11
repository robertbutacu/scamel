package bayes.classifier

import bayes.classifier.data._


/**
  * Pro and Cons of Naive Bayes:
  * Pro:
  *       - easy and fast to predict a class of test data set
  *       - performs better compared to other models assuming independence
  *
  * Cons:
  *       - doesn't work on data not present in the training set
  *           - can use other techniques to avoid this
  *       - known as a bad estimator
  *       - independent predictor assumption ( hard to achieve irl )
  */

/**
  *  1. How many positive/negative in conclusion -> probability.
  *  2. Individual probability for each attribute from the dataset -> count and probability
  *  3. Given new data to classify, compute P(X | Conclusion = Positive )
  * P(X | Conclusion = Negative )
  *  4. Divide both sides by evidence P(X) to normalize.
  * P(X) = Product Of Probability Of An Attribute to be equal to the Input Data's Attribute Value.
  */
object NaiveBayes {
  def apply(trainingData: List[Dataset], toClassify: List[Input], classData: Dataset): List[(Dataset, Attribute)] = {
    val classClassified = classDataClassifier(classData)

    val individualProbabilities = trainingData.map(t => trainingDataClassifier(t, classData))

    for (elem <- individualProbabilities) {println(elem)}

    List.empty
  }

  private def classDataClassifier(classData: Dataset): List[(Data, Double)] =
    classData.data
      .distinct
      .map(d => (d, classData.data.count(_ == d).toDouble / classData.data.length))

  private def trainingDataClassifier(trainingData: Dataset, classData: Dataset): IndividualProbability = {
    val combinedColumns = trainingData.data.zip(classData.data)

    val probabilities = combinedColumns.distinct
      .map(c => (c._1, c._2, combinedColumns.count(_ == c).toDouble / classData.data.count(_ == c._2).toDouble))
      .toSet

    IndividualProbability(trainingData.attribute, probabilities)
  }

}
