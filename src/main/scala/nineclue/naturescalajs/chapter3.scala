package nineclue.naturescalajs

import util.Random
import org.scalajs.dom

class SpiralDraw(val width:Int = 600, val height:Int = 400) extends CanvasEngine {
  val canvas:dom.HTMLCanvasElement = newCanvas("SpiralDraw", width, height)
  val description = "Chap.3 Drawing circles in spiral way"
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  ctx.fillStyle = "green"
  var theta = 0.0
  var r = 0.0

  def draw = {
    val x = r * Math.cos(theta)
    val y = r * Math.sin(theta)

    ctx.beginPath
    ctx.arc(x + width/2, y + height/2, 5, 0, Math.PI * 2)
    // c.stroke
    ctx.fill

    theta += 0.02
    r += 0.05
  }
}