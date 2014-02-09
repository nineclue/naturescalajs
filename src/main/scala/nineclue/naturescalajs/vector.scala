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
