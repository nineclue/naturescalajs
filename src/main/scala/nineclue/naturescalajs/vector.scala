package nineclue.naturescalajs

import util.Random
import org.scalajs.dom
import dom.{ HTMLCanvasElement, CanvasRenderingContext2D }

trait Vector

object Vector {
	def apply(x:Double, y:Double) = Vector2D(x, y)
	// def apply(x:Double, y:Double, z:Double) = Vector3d(x, y, z)
	def rand2D = {
		val a = Random.nextInt(360) * Math.PI / 180
		Vector2D(Math.sin(a), Math.cos(a))
	}
	val zero = Vector2D(0, 0)
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

class Mover(location:Vector2D, velocity:Vector2D) {
	var loc:Vector2D = location
	var vel:Vector2D = velocity
	var acc:Vector2D = Vector.zero
	var mass:Double = 10.0
	var maxVel:Double = 10.0
	var relocateF:Vector2D => Vector2D = ( (l:Vector2D) => l )

	var accelF:Option[Mover => Vector2D] = None
	var forceF:Option[Mover => Vector2D] = None

	def setAccelF(f: Mover => Vector2D) = {
		accelF = Some(f)
	}

	/* def setMaxVelocity(v:Double):Unit = (maxVel = v)

	def setMass(m:Double) = (mass = m)
	*/

	def setForceF(f: Mover => Vector2D) = (forceF = Some(f))

	def update = {
		forceF match {
			case Some(f) =>
				val force = f(this)
				acc = force / mass
			case None =>
				acc = Vector.zero
		}
		accelF match {
			case Some(f) =>
				acc += f(this)
				vel += acc
				if (vel.mag > maxVel) vel = vel.limit(maxVel)
			case None =>
		}
		loc += vel
		loc = relocateF(loc)
	}
}

class CanvasMover(location:Vector2D, velocity:Vector2D, val canvas:HTMLCanvasElement,
	val fill:String = "blue", val strike:String = "black") extends Mover(location, velocity) {
	val radius:Double = 30.0

	var draw:CanvasRenderingContext2D => Unit = { (c:CanvasRenderingContext2D) =>
    c.fillStyle = fill
    c.strokeStyle = strike
    c.beginPath
    c.arc(loc.x, loc.y, radius, 0, Math.PI * 2)
    c.stroke
    c.fill
	}

	relocateF = { (l:Vector2D) =>
		val x:Double =
			if (l.x >= (canvas.width + radius / 2)) -radius/2
			else if (l.x <= -radius/2) canvas.width + radius / 2
			else l.x
		val y:Double =
			if (l.y >= (canvas.height + radius / 2)) -radius/2
			else if (l.y <= -radius/2) canvas.height + radius / 2
			else l.y
		Vector2D(x, y)
	}
}