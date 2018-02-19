package id3.data

case class Dataset[A: Ordering, B](attribute: B,
                                   data: List[A])


object Dataset {
  def isUniqueData[A: Ordering, B](dataset: Dataset[A, B]): Boolean =
    dataset.data.distinct.lengthCompare(1) == 0
}