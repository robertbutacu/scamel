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
  def apply(trainingData: List[Dataset],
            toClassify: List[Input],
            classData: ClassAttribute): List[(Input, Boolean)] = {
    val classClassified = classDataClassifier(classData)

    val individualProbabilities = trainingData.map(t => trainingDataClassifier(t, classData))

    val positiveOutcome = toClassify.map(c =>
      getData(c, individualProbabilities, isHappening = true, classClassified))

    val negativeOutcome = toClassify.map(c =>
      getData(c, individualProbabilities, isHappening = false, classClassified))

    val probPosOutcome = positiveOutcome.map(e => (probability(e), true))
    val probNegOutcome = negativeOutcome.map(e => (probability(e), false))

    val evidence = toClassify.map(t => getEvidence(trainingData, t)).map(probability)

    toClassify.zipWithIndex.map(e =>
      classify(e,
        probPosOutcome(e._2)._1,
        probNegOutcome(e._2)._1,
        evidence(e._2))
    )
  }

  /**
    * Given a certain input( a dataset) , the function will compute whether the outcome will be positive or negative, based on
    * the computation of max(probNeg/evidence, probPos/evidence).
    *
    * @param input          - input data zipped with index
    * @param probPosOutcome - positive outcome probability
    * @param probNegOutcome - negative outcome probability
    * @param evidence       - evidence for the current input data
    * @return               - the outcome for the current input ( dataset )
    */
  private def classify(input: (Input, Int),
                       probPosOutcome: Double,
                       probNegOutcome: Double,
                       evidence: Double): (Input, Boolean) = {
    (
      input._1,
      if (probNegOutcome / evidence > probPosOutcome / evidence)
        false
      else true
    )
  }

  /**
    *
    * @param input - a list of probabilities of type part/total ( so already themselves a probability )
    * @return      - the product of all elements in the list
    */
  private def probability(input: List[Double]): Double =
    round(input.foldRight(1.0)((curr, total) => curr * total))


  /**
    *
    * @param classData
    * @return
    */
  private def classDataClassifier(classData: ClassAttribute): List[(Boolean, Double)] =
    classData.data
      .distinct
      .map(d => (d, round(classData.data.count(_ == d).toDouble / classData.data.length)))


  private def trainingDataClassifier(trainingData: Dataset, classData: ClassAttribute): IndividualProbability = {
    val combinedColumns = trainingData.data.zip(classData.data)

    val probabilities = combinedColumns.distinct
      .map(c =>
        (
          c._1, c._2,
          round(combinedColumns.count(_ == c).toDouble / classData.data.count(_ == c._2).toDouble)
        )
      )
      .toSet

    IndividualProbability(trainingData.attribute, probabilities)
  }

  /**
    * Input is of type (Attribute, Data) =>
    * going through individualProbabilities, only those fields where the data and the outcome match
    * are filtered and concatenated with the values from classData which match the isHappening variable.
    *
    * @param input                   - data for which it is wanted to find the probability of the output
    * @param individualProbabilities - every attribute's probability for each data
    * @param isHappening             - true/false depending on the wanted output
    * @param classData               - probability of negative/positive event happening
    * @return
    */
  private def getData(input: Input,
                      individualProbabilities: List[IndividualProbability],
                      isHappening: Boolean,
                      classData: List[(Boolean, Double)]): List[Double] =
    input.data.flatMap(i =>
      individualProbabilities
        .flatMap(d =>
          d.probabilities
            .filter(e => e._1 == i._2 && e._2 == isHappening)
            .toList
        )
    ).toList.map(_._3) ::: classData.filter(e => e._1 == isHappening).map(_._2)


  private def getEvidence(trainingData: List[Dataset],
                          input: Input,
                         ): List[Double] = {
    val filteredTrainingData = input.data.map(i =>
      trainingData
        .flatMap(d =>
          d.data
            .filter(e => d.attribute == i._1 && e == i._2))
    ).toList

    val probabilities = filteredTrainingData.map(f =>
      round(f.length.toDouble / trainingData.head.data.length.toDouble))

    probabilities
  }

  private def round(input: Double) = (input * 1000).floor / 1000
}
