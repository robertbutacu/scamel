package algorithms.kmeans

import common.data.BidimensionalPoint
import data.kmeans.structures.Cluster
import org.sameersingh.scalaplot.Implicits.{output, xyChart}
import org.sameersingh.scalaplot.Implicits._
import org.sameersingh.scalaplot.Style.Color
import org.sameersingh.scalaplot.XYPlotStyle

import scala.language.higherKinds

case class WithPlotter(path: String, projectName: String) {
  def plot(currClusters: List[Cluster[BidimensionalPoint, Double]], stepCount: Int): Unit = {
    val centroidsX = currClusters.map(c => c.centroid.point.X)
    val centroidsY = currClusters.map(c => c.centroid.point.Y)

    val pointsX = currClusters.flatMap(c => c.points.map(_.X))
    val pointsY = currClusters.flatMap(c => c.points.map(_.Y))

    output(PNG("docs/img/", "scatter2"), xyChart(
      (centroidsX ::: pointsX) -> Seq(
        Y(centroidsY, style = XYPlotStyle.Dots, color = Option(Color.Red)),
        Y(pointsY,    style = XYPlotStyle.Dots, color = Option(Color.Blue)))
    ))
  }
}
