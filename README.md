# scamel
Scamel is a small Scala library which features some essential machine learning algorithms:
  - Bayes Naive Classifier 
  - Id3
  - Clustering 
    - Hierarchical, Top-down
    - K-Means
    
# How-to

- Naive Bayes Classifier

- Id3 - the algorithm's result is a Tree where the nodes are attributes which classify data, the leafs are the conclusion based on the characteristics and the arc between 2 nodes represents the condition necessary to move down the tree.

  In order to use the algorithm, the user needs to input some `Dataset`s, which is composed of an `Attribute` and a list of data.
  
  
  The library provides a function `Id3Classifier.classify[A: Ordering, B](id3Tree: Node[A, B], toClassify: Map[B, A]): Option[A]` which, given a node representing the root of an Id3Tree, and a Map of data from B to A representing an attribute of the entity which is to be classified and the value for that attribute, it will try to do that based on that tree and give an answer if that is possible. 
  
  
  The algorithm `Id3[A: Ordering, B](conclusion: Dataset[A, B], trainingData: List[Dataset[A, B]])` takes 2 parameters: 
    - the conclusion: a dataset representing what the actual goal of the classifying is, the attribute to classify
    - the data      : a list of datasets which represents a characteristic of the corresponding index of the conclusion data.
    
  
  To note that the order of the elements in the list is relevant and is not to be taken lightly, as it may give out different results - through this arrangement, it is tried to simulate a table, where every single row is data entry with a list of characteristics and a final attribute that it is inferred.
  
  To see a prettyPrinter() version of a result tree, there is a `Id3.prettyPrinter(node: Node[A, B])` function which does that.

- Clustering
  Both clustering algorithms are generic, being agnostic of the number of the dimensions each point has.
  The Hierarchical clustering also is agnostic of the distance calculator between points as well.
  This makes the algorithms extremely extensible and customizable, given that the appropriate implicits are defined.
  
  - Hierarchical, Top-down
  
  - K-Means
