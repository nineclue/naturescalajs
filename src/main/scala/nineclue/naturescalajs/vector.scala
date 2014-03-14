package nineclue.naturescalajs

import util.Random
import org.scalajs.dom
import dom.{ HTMLCanvasElement, CanvasRenderingContext2D }

trait Vector

object Vector {
	type Degree = Double
	type Radian = Double
	implicit def deg2rad(d:Degree):Radian = d * Math.PI / 180

	def apply(x:Double, y:Double) = Vector2D(x, y)
	// def apply(x:Double, y:Double, z:Double) = Vector3d(x, y, z)
	def rand2D = {
		// val a:Radian = Random.nextInt(360) * Math.PI / 180
		val a:Radian = Random.nextInt(360):Degree
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
	var ang:Double = 0.0
	var aVel:Double = 0.0
	var aAcc:Double = 0.0

	var accelF:Option[Mover => Vector2D] = None
	var forceF:Option[Mover => Vector2D] = None
	var aAccelF:Option[Mover => Double] = None

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
			case None =>
		}
		vel += acc
		if (vel.mag > maxVel) vel = vel.limit(maxVel)
		loc += vel

		aAccelF match {
			case Some(f) =>
				aAcc = f(this)
			case None =>
				aAcc = 0.0
		}
		aVel += aAcc
		ang += aVel
	}
}

class CanvasMover(location:Vector2D, velocity:Vector2D, val canvas:HTMLCanvasElement,
	val fill:String = "blue", val strike:String = "black") extends Mover(location, velocity) {
	var radiusF:() => Double = () => mass * 3
	var radius = radiusF()
	var relocateF:Option[CanvasMover => Unit] = None

	def reRadius = ( radius = radiusF() )

	var draw:CanvasRenderingContext2D => Unit = { (c:CanvasRenderingContext2D) =>
    c.fillStyle = fill
    c.strokeStyle = strike
    c.beginPath
    c.arc(loc.x, loc.y, radius, 0, Math.PI * 2)
    c.stroke
    c.fill()
	}

	override def update = {
		super.update
		relocateF match {
			case Some(f) =>
				f(this)
			case None =>
		}
	}

	val otherside = { (m:CanvasMover) =>
		val x:Double =
			if (m.loc.x >= (canvas.width + radius / 2)) -radius/2
			else if (m.loc.x <= -radius/2) canvas.width + radius / 2
			else m.loc.x
		val y:Double =
			if (m.loc.y >= (canvas.height + radius / 2)) -radius/2
			else if (m.loc.y <= -radius/2) canvas.height + radius / 2
			else m.loc.y
		m.loc = Vector2D(x, y)
	}

	relocateF = Some(otherside)
}