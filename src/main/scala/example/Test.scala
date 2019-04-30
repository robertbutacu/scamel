package example

import org.sameersingh.scalaplot.Implicits._
import org.sameersingh.scalaplot.XYPlotStyle

object Test extends App {
  val x = 0.0 until 10.0 by 0.01
  val rnd = new scala.util.Random(0)

  output(PNG("docs/img/", "scatter2"), xyChart(
    x -> Seq(Y(x.map(_ + rnd.nextDouble - 0.5), style = XYPlotStyle.Dots)),
  ))
}
