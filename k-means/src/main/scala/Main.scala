import kmeans.KMeans
import kmeans.structures.Point

object Main extends App {

  //A(2, 10), B(2, 5), C(8, 4), D(5, 8), E(7, 5), F(6, 4), G(1, 2), H(4, 9).

  KMeans.findClusters(3,
    List(
      Point("A", 2.0, 10.0),
      Point("B", 2.0, 5.0),
      Point("C", 8.0, 4.0),
      Point("D", 5.0, 8.0),
      Point("E", 7.0, 5.0),
      Point("F", 6.0, 4.0),
      Point("G", 1.0, 2.0),
      Point("H", 4.0, 9.0)
    )).foreach(cluster => println(cluster))
}
