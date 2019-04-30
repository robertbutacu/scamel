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
    val centroidsX = currClusters.map(c => c.centroid.point.X)
    val centroidsY = currClusters.map(c => c.centroid.point.Y)

    val pointsX = currClusters.flatMap(c => c.points.map(_.X))
    val pointsY = currClusters.flatMap(c => c.points.map(_.Y))


    val dataSets = currClusters.map{ c =>
      val data = new MemXYSeries(c.points.map(_.X), c.points.map(_.Y), c.centroid.name)
      data.color = Color.values.toList(Random.nextInt(Color.values.size))
      data.pointType = Option(PointType.+)
      data.plotStyle = XYPlotStyle.Dots

      data
    }
    val data = new XYData()

    dataSets.foldLeft(()){(_, c) =>
      data += c
      ()
    }

    val chart = new XYChart(projectName, data)

    val plotter = new GnuplotPlotter(chart)
    plotter.png(path, projectName + "_" + stepCount)
  }
}
