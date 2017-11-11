package bayes.classifier.data

case class Dataset(attribute: Attribute, data: List[Data])

case class ClassAttribute(attribute: Attribute, data: List[Boolean])

case class Input(data: Set[(Attribute, Data)])

case class IndividualProbability(attribute: Attribute, probabilities: Set[(Data, Boolean, Double)])