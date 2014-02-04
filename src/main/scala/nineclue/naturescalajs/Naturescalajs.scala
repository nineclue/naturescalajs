package nineclue.naturescalajs

import scala.scalajs.js
import org.scalajs.dom
import js.Dynamic.{ global => g, literal => lit }
import util.Random

object Naturescalajs {
  def main(): Unit = {
  	// new RandomWalk("playground", 640, 480)
  	// (1 to 100).foreach(i => println(s"$i : ${Utility.noise(i)}"))
  	new NoiseDraw("playground", 600, 600)
	}

}

abstract class Engine(parentID:String, width:Int = 640, height:Int = 480, fps:Int = 30) {
	val canvas = g.document.createElement("canvas").asInstanceOf[dom.HTMLCanvasElement]
	canvas.id = "engine"
	canvas.width = width
	canvas.height = height
	canvas.style.border = "1px solid gray"
	g.document.getElementById(parentID).appendChild(canvas)
	val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

	def draw:Unit
	dom.setInterval(() => draw, 1000 / fps)
}
