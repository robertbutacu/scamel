package algorithms.kmeans

import common.data.BidimensionalPoint
import data.kmeans.structures.Cluster
import org.sameersingh.scalaplot.Implicits._
import org.sameersingh.scalaplot.Style.{Color, PointType}
import org.sameersingh.scalaplot.gnuplot.GnuplotPlotter
import org.sameersingh.scalaplot.{MemXYSeries, XYChart, XYData, XYPlotStyle}

import scala.language.higherKinds

case class WithPlotter(path: String, projectName: String) {
  def plot(currClusters: List[Cluster[BidimensionalPoint, Double]], stepCount: Int): Unit = {
    val centroidsX = currClusters.map(c => c.centroid.point.X)
    val centroidsY = currClusters.map(c => c.centroid.point.Y)

    val pointsX = currClusters.flatMap(c => c.points.map(_.X))
    val pointsY = currClusters.flatMap(c => c.points.map(_.Y))

    val centroidsPoints = new MemXYSeries(centroidsX, centroidsY, "Centroids")
    val points          = new MemXYSeries(pointsX, pointsY, "Points")

    centroidsPoints.plotStyle = XYPlotStyle.Dots
    points.plotStyle          = XYPlotStyle.Dots

    centroidsPoints.pointType = Option(PointType.+)
    points.pointType          = Option(PointType.+)

    centroidsPoints.color = Color.Red
    points.color          = Color.Blue

    val data = new XYData(points)
    data += centroidsPoints

    val chart = new XYChart(projectName, data)

    val plotter = new GnuplotPlotter(chart)
    plotter.png(path, projectName + "_" + stepCount)
  }
}
