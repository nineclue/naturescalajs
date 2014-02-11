package nineclue.naturescalajs

import util.Random
import org.scalajs.dom

class BouncingBall(val width:Int = 640, val height:Int = 360) extends CanvasEngine {
  private var x:Double = 100
  private var y:Double = 100
  private var xspeed:Double = 1.0
  private var yspeed:Double = 3.3
  private val radius = 16

  val canvas:dom.HTMLCanvasElement = newCanvas("Bouncing Ball", width, height)
  val description = "Chap.1 Bouncing Ball"
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  ctx.strokeStyle = "black"
  ctx.fillStyle = "rgb(175, 175, 175)"

  def draw = {
    ctx.clearRect(0,0,width,height)

    x += xspeed
    y += yspeed

    if ((x > (width - radius / 2)) || (x < radius / 2)) xspeed *= -1
    if ((y > (height - radius / 2)) || (y < radius / 2)) yspeed *= -1

    ctx.beginPath
    ctx.arc(x, y, radius, 0, Math.PI*2)
    ctx.fill
    ctx.stroke
  }
}

class VectorMagnitude(val width:Int = 300, val height:Int = 300) extends CanvasEngine {
  val canvas:dom.HTMLCanvasElement = newCanvas("Vector Magnitude", width, height)
  val description = "Example 1.5 Vector Magnitude"
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  override val trackMouse = true

  ctx.fillStyle = "blue"
  ctx.strokeStyle = "black"
  def remap = Utility.map(0, Math.sqrt(Math.pow(width/2, 2) + Math.pow(height/2, 2)), 0, width)_
  val center = Vector(width / 2, height / 2)

  def draw = {
    ctx.clearRect(0,0,width,height)

    val m = Vector(mx, my)

    ctx.fillRect(0, 0, remap((m-center).mag), 10)

    // ctx.translate(width/2, height/2)

    ctx.beginPath
    ctx.moveTo(width/2, height/2)
    ctx.lineTo(m.x, m.y)
    ctx.stroke
  }
}