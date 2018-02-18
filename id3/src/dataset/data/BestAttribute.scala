package dataset.data

case class BestAttribute[A: Ordering, B](attribute: B,
                                         subsets: List[Subset[A, B]])
