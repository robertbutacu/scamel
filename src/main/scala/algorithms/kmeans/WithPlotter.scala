package algorithms.kmeans

import common.data.BidimensionalPoint
import data.kmeans.structures.Cluster
import org.sameersingh.scalaplot.Implicits._
import org.sameersingh.scalaplot.Style.{Color, PointType}
import org.sameersingh.scalaplot.gnuplot.GnuplotPlotter
import org.sameersingh.scalaplot.{MemXYSeries, XYChart, XYData, XYPlotStyle}

import scala.language.higherKinds
import scala.util.Random

case class WithPlotter(path: String, projectName: String) {
  def plot(currClusters: List[Cluster[BidimensionalPoint, Double]], stepCount: Int): Unit = {
    val dataSets = currClusters.map{ c =>
      val data = new MemXYSeries(c.points.map(_.X), c.points.map(_.Y), c.centroid.name)
      data.color = Color.values.toList(Random.nextInt(Color.values.size))
      data.pointType = Option(PointType.Dot)
      data.pointSize = Option(2.0)
      data.plotStyle = XYPlotStyle.Points

      data
    }

    val centroidsPoints = currClusters.foldLeft((Seq.empty[Double], Seq.empty[Double])){
      (acc, curr) =>
        (acc._1 :+ curr.centroid.point.X, acc._2 :+ curr.centroid.point.Y)
    }

    val centroidSeries = new MemXYSeries(centroidsPoints._1, centroidsPoints._2, "Centroids")
    centroidSeries.color = Color.Black
    centroidSeries.pointType = Option(PointType.+)
    centroidSeries.plotStyle = XYPlotStyle.Points
    centroidSeries.pointSize = Option(2.5)

    val data = new XYData()

    dataSets.foreach { c =>
      data += c
    }

    data += centroidSeries

    val chart = new XYChart(projectName, data)

    val plotter = new GnuplotPlotter(chart)
    plotter.png(path, projectName + "_" + stepCount)
  }
}
