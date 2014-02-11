package nineclue.naturescalajs

import util.Random
import org.scalajs.dom

trait Vector

object Vector {
	def apply(x:Double, y:Double) = Vector2D(x, y)
	// def apply(x:Double, y:Double, z:Double) = Vector3d(x, y, z)
}

case class Vector2D(x:Double, y:Double) extends Vector {
	def +(o:Vector2D) = Vector2D(x + o.x, y + o.y)
	def -(o:Vector2D) = Vector2D(x - o.x, y - o.y)
	def *(s:Double) = Vector2D(x * s, y * s)
	def /(s:Double) = Vector2D(x / s, y / s)
	def mag() = Math.sqrt(x*x + y*y)
	def norm() = this / this.mag()
}

case class Vector3D(x:Double, y:Double, z:Double) extends Vector