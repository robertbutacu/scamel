package dataset.data


//From a best attribute, form a node with BestAttribute's attribute value, and the nodes represented by
// (Subset's attribute, and the recursive call to Id3
case class Subset[A: Ordering, B](attribute: A,
                  table: List[Dataset[A, B]],
                  conclusion: Dataset[A, B])
