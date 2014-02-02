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
		/*
		ctx.fillStyle = "white"
		ctx.fillRect(0, 0, width, height)

		ctx.fillStyle = "rgb(200, 0, 0)"
		ctx.fillRect(x, y, 55, 50)

		ctx.fillStyle = "rgba(0, 0, 200, 0.5)"
		ctx.fillRect(30, 30, 55, 50)

		x += 2
		if (x >= width) x = 0
		*/
	}
}