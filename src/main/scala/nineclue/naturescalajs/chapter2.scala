package nineclue.naturescalajs

import util.Random
import org.scalajs.dom

class Forces0(val width:Int = 640, val height:Int = 360) extends CanvasEngine {
  val canvas:dom.HTMLCanvasElement = newCanvas("Forces0", width, height)
  val description = "Chap.2 Bouncing ball under the wind"
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  def draw = ???
}