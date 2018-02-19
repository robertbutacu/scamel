package id3.data.tree

case class Leaf[A: Ordering](attribute: A)

