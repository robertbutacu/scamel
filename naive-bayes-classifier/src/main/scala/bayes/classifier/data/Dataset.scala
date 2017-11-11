package bayes.classifier.data

case class Dataset(attribute: Attribute, data: List[Data])

case class Input(data: Set[(Attribute, Data)])

