package nineclue.naturescalajs

import util.Random
import org.scalajs.dom

class RandomWalk(width:Int = 300, height:Int = 300) extends CanvasEngine {
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

class RandomGraph(width:Int = 500, height:Int = 300) extends CanvasEngine {
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

class NoiseGraph(val width:Int = 500, val height:Int = 300) extends CanvasEngine {
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

class NoiseWalk(width:Int = 300, height:Int = 300) extends CanvasEngine {
	private var x:Double = width / 2
	private var y:Double = height / 2
	private var tx:Double = 0.0
	private var ty:Double = 10000.0
	val canvas:dom.HTMLCanvasElement = newCanvas("Noise Walk", width, height)
	val description = "Moving circle according to simplex noise"
	private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
	private def remap = Utility.map(-1, 1, -2, 2)_

	ctx.strokeStyle = "rgba(100,100,100,0.2)"
	ctx.fillStyle = "rgba(45,90,180,0.2)"

	def draw = {
		x += remap(Utility.noise(tx))
		y += remap(Utility.noise(ty))
		tx += 0.01
		ty += 0.01

		ctx.beginPath
		ctx.arc(x, y, 30, 0, Math.PI * 2)
		ctx.stroke
		ctx.fill
	}
}

class TwoDNoise(val width:Int = 200, val height:Int = 200) extends CanvasEngine {
	var tt:Double = 0
	val canvas:dom.HTMLCanvasElement = newCanvas("2D Noise", width, height, 100)
	val description = "Cloud like image made by 2D simplex noise"
	private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
	private def remap = Utility.map(-1, 1, 0, 255)_
	private def xmap = Utility.map(0, width, 0, width*0.01)_
	private def ymap = Utility.map(0, height, 0, height*0.01)_
	private val iData = ctx.getImageData(0, 0, width, height)

	def draw = {
		for {
			y <- 0 until height
			x <- 0 until width
		} {
			val pos = (x + y*width) * 4
			val density = remap(Utility.noise(x*0.01, y*0.01, tt)).toInt

			/*
			ctx.beginPath
			ctx.fillStyle = s"rgb($density, $density, $density)"
			ctx.fillRect(x, y, 1, 1)
			ctx.stroke
			*/

			(0 to 2).foreach { i => iData.data.update(pos+i, density) }
			iData.data.update(pos+3, 255)
		}
		ctx.putImageData(iData, 0, 0)
		tt += 0.05
	}
}