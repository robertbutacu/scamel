package dataset.data.tree

case class Leaf[A <: Ordering[A]](attribute: A)

