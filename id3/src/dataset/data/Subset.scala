package dataset.data

case class Subset[A <: Ordering[A], B](attribute: A,
                  table: List[Dataset[A, B]],
                  conclusion: Dataset[A, B])
