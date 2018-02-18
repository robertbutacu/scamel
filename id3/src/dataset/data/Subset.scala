package dataset.data

case class Subset(attribute: String,
                  table: List[Dataset],
                  conclusion: Dataset)
