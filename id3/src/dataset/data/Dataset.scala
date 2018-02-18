package dataset.data

case class Dataset[A <: Ordering[A], B](attribute: B,
                   data: List[A])
