package id3.data

case class Dataset[A: Ordering, B](attribute: B,
                                   data: List[A])
