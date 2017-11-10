package bayes.classifier

case class Dataset(attribute: Attribute, data: List[Data])

case class Input(attributes: Set[Attribute], data: Set[Data])

