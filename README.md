# scamel
Scamel is a small Scala library which features some essential machine learning algorithms:
  - Bayes Naive Classifier 
  - Id3
  - Clustering 
    - Hierarchical, Top-down
    - K-Means
    
# How-to

1. Naive Bayes Classifier

2. Id3 

3. Clustering
  Both clustering algorithms are generic, being agnostic of the number of the dimensions each point has.
  The Hierarchical clustering also is agnostic of the distance calculator between points as well.
  This makes the algorithms extremely extensible and customizable, given that the appropiate implicits are defined.
  
  3.1 Hierarchical, Top-down
  
  3.2 K-Means
