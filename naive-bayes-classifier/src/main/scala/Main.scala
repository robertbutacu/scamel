import bayes.classifier.{Dataset, Input, NaiveBayes}

object Main extends App {
  NaiveBayes(
    List(
      Dataset("outlook", List("sunny", "sunny", "overcast", "rainy", "rainy", "rainy", "overcast",
        "sunny", "sunny", "rainy", "sunny", "overcast", "overcast", "rainy")),
      Dataset("temperature", List("hot", "hot", "hot", "mild", "cool", "cool", "cool", "mild", "cool", "mild",
        "mild", "mild", "hot", "mild")),
      Dataset("humidity", List("high", "high", "high", "high", "normal", "normal", "normal", "high",
        "normal", "normal", "normal", "high", "normal", "high")),
      Dataset("windy", List("false", "true", "false", "false", "false", "true", "true", "false", "false", "false",
        "true", "true", "false", "true"))),
    List(
      Input(Set("outlook", "temperature", "humidity", "wind"), Set("sunny", "cool", "high", "strong"))
    ),
    Dataset("play", List("no", "no", "yes", "yes", "yes", "no", "yes", "no", "yes", "yes", "yes", "yes", "yes", "no"))
  )
}