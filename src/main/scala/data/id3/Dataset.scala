package data.id3

case class Dataset[A, B](attribute: B, data: List[A])

object Dataset {
  def isUniqueData[A, B](dataset: Dataset[A, B]): Boolean = dataset.data.distinct.lengthCompare(1) == 0
}