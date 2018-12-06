# scamel
Scamel is a small Scala library which features some essential machine learning algorithms:
  - Bayes Naive Classifier 
  - Id3
  - Clustering 
    - Hierarchical, Top-down
    - K-Means
    
# How-to

- Naive Bayes Classifier

- Id3 - the algorithm's result is a Tree where the nodes/leafs are classified data and there is also the notion of the arc, which represents the exact value necessary to move down the tree.

  In order to use the algorithm, the user needs to input some `Dataset`s, which is composed of an `Attribute` and a list of data.
  
  
  The library provides a function which, given a node representing the root of an Id3Tree, and a Map of data from A to B representing the characteristics of the entity which is to be classified, it will try to do that based on that tree and give an answer if that is possible.
  
  
  The algorithm takes 2 parameters: 
    - the conclusion: a dataset representing what the actual goal of the classifying is
    - the data      : a list of datasets which represents a characteristic of the corresponding index of the conclusion data.
    
  
  To note that the order of the list is relevant and is not to be taken lightly, as it may give out different results.
  

- Clustering
  Both clustering algorithms are generic, being agnostic of the number of the dimensions each point has.
  The Hierarchical clustering also is agnostic of the distance calculator between points as well.
  This makes the algorithms extremely extensible and customizable, given that the appropiate implicits are defined.
  
  - Hierarchical, Top-down
  
  - K-Means
