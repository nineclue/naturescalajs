package nineclue.naturescalajs

import util.Random
import org.scalajs.dom

class RandomWalk(width:Int = 400, height:Int = 400) extends CanvasEngine {
	private var x:Int = width / 2
	private var y:Int = height / 2
	val canvas:dom.HTMLCanvasElement = newCanvas("Random Walk", width, height)
	val description = "Randomly moving dot"
	private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

	ctx.fillStyle = "rgb(0,0,0)"

	def draw = {
		ctx.fillRect(x, y, 1, 1)

		val i = Random.nextInt(4)
		if (i == 0) x += 1
		else if (i == 1) x -= 1
		else if (i == 2) y += 1
		else if (i == 3) y -= 1
	}
}

class RandomGraph(width:Int = 600, height:Int = 300) extends CanvasEngine {
	private var x = 0
	private val vals = new Array[Int](width)
	val canvas:dom.HTMLCanvasElement = newCanvas("Random Graph", width, height)
	val description = "Graph of random value"
	private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

	ctx.fillStyle = "black"
	ctx.strokeStyle = "black"

	private def remap = Utility.map(Int.MinValue, Int.MaxValue, 0, canvas.height)_

	def draw = {
		val i = x % width
		val v = remap(Random.nextInt).toInt
		vals(i) = v
		ctx.clearRect(0, 0, width, height)
		ctx.beginPath
		if (x >= width)
			((i+1) until width).foreach(n => ctx.lineTo(n-i, vals(n)))
		(0 to i).foreach(n => ctx.lineTo(n + (width - i), vals(n)))
		ctx.stroke
		x += 1
	}
}

class NoiseGraph(val width:Int = 600, val height:Int = 300) extends CanvasEngine {
	private var x = 0
	private var t:Double = 0.0
	private val vals = new Array[Int](width)
	val canvas:dom.HTMLCanvasElement = newCanvas("Noise Graph", width, height)
	val description = "Graph of Simplex Noise"
	private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

	ctx.fillStyle = "black"
	ctx.strokeStyle = "black"

	private def remap = Utility.map(-1, 1, 0, canvas.height)_

	def draw = {
		val i = x % width
		val v = remap(Utility.noise(t)).toInt
		vals(i) = v
		ctx.clearRect(0, 0, width, height)
		ctx.beginPath
		if (x >= width)
			((i+1) until width).foreach(n => ctx.lineTo(n-i, vals(n)))
		(0 to i).foreach(n => ctx.lineTo(n + (width - i), vals(n)))
		ctx.stroke
		x += 1
		t += 0.02
	}

}