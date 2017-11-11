package bayes.classifier

import bayes.classifier.data._


/** Some of real world examples are:
  *
  *   - To mark an email as spam or not spam
  *   - Classify a news article about technology, politics, or sports
  *   - Check a piece of text expressing positive emotions, or negative emotions?
  *   - Used for face recognition software.
  *
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
    //prob for true/false
    val classClassified = classDataClassifier(classData)

    //each attribute split into sub-table with respective probability
    val individualProbabilities = trainingData.map(t => trainingDataClassifier(t, classData))

    // probabilities for positiveOutcome for each input data
    val positiveOutcome = toClassify.map(c =>
      getData(c, individualProbabilities, isHappening = true, classClassified))

    //probabilities for negativeOutcome for each input data
    val negativeOutcome = toClassify.map(c =>
      getData(c, individualProbabilities, isHappening = false, classClassified))

    //computing overall probability for positive/negative
    val probPosOutcome = positiveOutcome.map(e => (probability(e), true))
    val probNegOutcome = negativeOutcome.map(e => (probability(e), false))

    //getting evidence so the final question can be answered
    // what is the higher probability of sth to happen?
    val evidence = toClassify.map(t => getEvidence(trainingData, t)).map(probability)

    // the final answer for a given input is max of (probNeg/evidence, probPos/evidence)
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
    * @return - the outcome for the current input ( dataset )
    */
  private def classify(input: (Input, Int),
                       probPosOutcome: Double,
                       probNegOutcome: Double,
                       evidence: Double): (Input, Boolean) =
    (
      input._1,
      if (probNegOutcome / evidence > probPosOutcome / evidence)
        false
      else true
    )

  /**
    *
    * @param input - a list of probabilities of type part/total ( so already themselves a probability )
    * @return - the product of all elements in the list
    */
  private def probability(input: List[Double]): Double =
    round(input.foldRight(1.0)((curr, total) => curr * total))


  /**
    *
    * @param classData - the outcome
    * @return - a list ( composed of 2 elements, ofc - (true, double), (false, double) with the probability
    *         of each respective boolean values.
    */
  private def classDataClassifier(classData: ClassAttribute): List[(Boolean, Double)] =
    classData.data
      .distinct
      .map(d => (d, round(classData.data.count(_ == d).toDouble / classData.data.length)))


  /**
    *
    * @param trainingData - a dataset aka a column from the input data
    * @param classData    - the outcome column
    * @return - IndividualProbability for that column ( go to declaration of IP for more details )
    */
  private def trainingDataClassifier(trainingData: Dataset, classData: ClassAttribute): IndividualProbability = {
    //so the probabilities can be computed easier
    val combinedColumns = trainingData.data.zip(classData.data)

    val probabilities = combinedColumns.distinct
      .map(c =>
        (
          c._1, c._2, //Data, Boolean Value
          // division of favorable cases of current Data value from the dataset to happen
          // and the total number of cases where the outcome matches current row's outcome
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
    * What is actually happening here is computing
    * P(X | isHappening = BooleanValue) = (Product(x belongs to X) of P(X = x | isHappening)) * P(isHappening)
    *
    * @param input                   - data for which it is wanted to find the probability of the output
    * @param individualProbabilities - every attribute's probability for each data
    * @param isHappening             - true/false depending on the wanted output
    * @param classDataProbabilities  - probability of negative/positive event happening
    * @return - a list of probabilities for every type of (RowValue, BooleanValue) to happen.
    */
  private def getData(input: Input,
                      individualProbabilities: List[IndividualProbability],
                      isHappening: Boolean,
                      classDataProbabilities: List[(Boolean, Double)]): List[Double] = {
    val probabilities = for {
      //list of Attributes( think weather ) and their respective Data value ( think Sunny )
      data <- input.data.toList

      // probability of each Data value to happen (ie: Weather Sunny True 0.5, except a list )
      individualProb <- individualProbabilities

      // each individual element of that list from above
      individualProbData <- individualProb.probabilities.toList

      // match Data value and Boolean value
      if individualProbData._1 == data._2 && individualProbData._2 == isHappening
    } yield individualProbData._3

    probabilities ::: classDataProbabilities.filter(e => e._1 == isHappening).map(_._2)
  }


  /**
    *
    * @param trainingData - all training data
    * @param input        - the values for which the outcome is wanted to be known
    * @return - evidence for every training data
    *         -> used to compute the outcome of a single row from input
    */
  private def getEvidence(trainingData: List[Dataset],
                          input: Input,
                         ): List[Double] = {
    // Evidence is represented by the count of input.data(x) divided by the length rows in the table
    // aka probability of data Y to appear in a random selection.
    val appearancesOfData = for {
      data <- input.data.toList // for each (Attribute, Data)
      td <- trainingData // look through training data
      probabilities <- td.data // in their respective data list
      if probabilities == data._2 // for the appearance of the Data value from the tuple above
    } yield data._2 // only interested in the value of Data, used later for count to apply the formula described above

    val filteredTrainingDataCount = appearancesOfData.distinct.map(d => appearancesOfData.count(_ == d).toDouble)

    //since list wont be randomised or anything similar, it is known that for a certain input of type
    // (Outlook, Sunny), (Weather, Strong)
    // will result in filteredTrainingDataCount to look like
    // List(List(Sunny, Sunny, Sunny), List(Strong, Strong, Strong)) - ofc, it will be mapped to List[Double] above
    val probabilities = filteredTrainingDataCount.map(f =>
      round(f / trainingData.head.data.length.toDouble))

    probabilities
  }

  //to not have any doubles with 10-20 digits hehe
  private def round(input: Double) = (input * 1000).floor / 1000
}
