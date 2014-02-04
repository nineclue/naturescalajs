package nineclue.naturescalajs

import util.Random

class RandomWalk(parent:String, width:Int, height:Int) extends Engine(parent, width, height) {
	var x:Int = width / 2
	var y:Int = height / 2

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

class RandomDraw(parent:String, width:Int, height:Int) extends Engine(parent, width, height) {
	var x = 0
	val vals = new Array[Int](width)

	ctx.fillStyle = "black"
	ctx.strokeStyle = "black"
	def remap = Utility.map(Int.MinValue, Int.MaxValue, 0, canvas.height)_

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

class NoiseDraw(parent:String, val width:Int, val height:Int) extends Engine(parent, width, height) {
	var x = 0
	var t:Double = 0.0
	val vals = new Array[Int](width)

	ctx.fillStyle = "black"
	ctx.strokeStyle = "black"
	def remap = Utility.map(-1, 1, 0, canvas.height)_

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