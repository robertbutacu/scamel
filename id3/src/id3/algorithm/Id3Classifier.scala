package id3.algorithm

import id3.data.tree.Node

object Id3Classifier {
  def classify[A: Ordering, B](id3Tree: Node[A, B], toClassify: Map[String, String]): Option[String] = {
    None
  }
}
