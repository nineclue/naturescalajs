package nineclue.naturescalajs

import scala.scalajs.js
import org.scalajs.dom
import js.Dynamic.{ global => g, literal => lit }
import util.Random

object Naturescalajs {
  def main(): Unit = {
  	new RandomWalk("playground", 640, 480)
	}

}

abstract class Engine(parentID:String, val width:Int = 640, val height:Int = 480, val fps:Int = 30) {
	val canvas = g.document.createElement("canvas").asInstanceOf[dom.HTMLCanvasElement]
	canvas.id = "engine"
	canvas.width = width
	canvas.height = height
	g.document.getElementById(parentID).appendChild(canvas)
	val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

	def draw:Unit
	dom.setInterval(() => draw, 1000 / fps)
}
