import bayes.classifier._
import bayes.classifier.data._

object Main extends App {
  println(NaiveBayes(
    List(
      Dataset(Outlook, List(Sunny, Sunny, Overcast, Rainy, Rainy, Rainy, Overcast,
        Sunny, Sunny, Rainy, Sunny, Overcast, Overcast, Rainy)),
      Dataset(Temperature, List(Hot, Hot, Hot, Mild, Cool, Cool, Cool, Mild, Cool, Mild,
        Mild, Mild, Hot, Mild)),
      Dataset(Humidity, List(High, High, High, High, Normal, Normal, Normal, High,
        Normal, Normal, Normal, High, Normal, High)),
      Dataset(Windy, List(False, True, False, False, False, True, True, False, False, False,
        True, True, False, True))),
    List(
      Input(Set((Outlook, Sunny), (Temperature, Cool), (Humidity, High), (Windy, True)))
    ),
    ClassAttribute(Play, List(false, false, true, true, true, false, true, false, true, true, true, true, true, false))
  ))
}