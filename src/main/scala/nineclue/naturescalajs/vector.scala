package nineclue.naturescalajs

import util.Random
import org.scalajs.dom

trait Vector

object Vector {
	def apply(x:Double, y:Double) = Vector2D(x, y)
	// def apply(x:Double, y:Double, z:Double) = Vector3d(x, y, z)
	def rand2D = {
		val a = Random.nextInt(360) * Math.PI / 180
		Vector2D(Math.sin(a), Math.cos(a))
	}
}

case class Vector2D(x:Double, y:Double) extends Vector {
	def +(o:Vector2D) = Vector2D(x + o.x, y + o.y)
	def -(o:Vector2D) = Vector2D(x - o.x, y - o.y)
	def *(s:Double) = Vector2D(x * s, y * s)
	def /(s:Double) = Vector2D(x / s, y / s)
	def mag() = Math.sqrt(x*x + y*y)
	def norm() = this / this.mag()
	def limit(vel:Double) = {
		val cL = mag
		Vector2D(x * vel / cL, y * vel / cL)
	}
}

// case class Vector3D(x:Double, y:Double, z:Double) extends Vector

class Mover(location:Vector2D, velocity:Vector2D, val maxX:Double, val maxY:Double, val maxVel:Double = 10.0) {
	var loc:Vector2D = location
	var vel:Vector2D = velocity
	var accelF:Option[Mover => Vector2D] = None

	def setAccelF(f: Mover => Vector2D) = {
		accelF = Some(f)
	}

	def update = {
		accelF match {
			case Some(f) =>
				vel += f(this)
				if (vel.mag > maxVel) vel = vel.limit(maxVel)
			case None =>
		}
		loc += vel
		if (loc.x > maxX) loc = Vector2D(0, loc.y)
		else if (loc.x < 0) loc = Vector2D(maxX, loc.y)
		if (loc.y > maxY) loc = Vector2D(loc.x, 0)
		else if (loc.y < 0) loc = Vector2D(loc.x, maxY)
	}
}