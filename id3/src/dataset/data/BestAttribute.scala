package dataset.data

case class BestAttribute[A <: Ordering[A], B](attribute: B,
                         subsets: List[Subset[A, B]])
