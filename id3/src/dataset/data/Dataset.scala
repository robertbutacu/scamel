package dataset.data

case class Dataset[A: Ordering, B](attribute: B,
                                   data: List[A])
