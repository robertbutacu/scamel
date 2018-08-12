package id3.data

case class BestAttribute[A, B](attribute: B, subsets: List[Subset[A, B]])
