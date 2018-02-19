package id3.data


//From a best attribute, form a node with BestAttribute's attribute value, and the nodes represented by
// (Subset's attribute, and the recursive call to Id3
case class Subset[A: Ordering, B](attribute: A,
                                  table: List[Dataset[A, B]],
                                  conclusion: Dataset[A, B])

object Subset {
  def isUniqueConclusion[A: Ordering, B](subset: Subset[A, B]): Boolean =
    subset.conclusion.data.distinct.lengthCompare(1) == 0
}
