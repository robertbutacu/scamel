package data.id3

case class BestAttribute[A, B](attribute: B, subsets: List[Subset[A, B]])
