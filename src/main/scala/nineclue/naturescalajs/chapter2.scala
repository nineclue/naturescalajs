package nineclue.naturescalajs

import util.Random
import org.scalajs.dom

class Forces0(val width:Int = 640, val height:Int = 360) extends CanvasEngine {
  val canvas:dom.HTMLCanvasElement = newCanvas("Forces0", width, height)
  val description = "Chap.2 Bouncing ball under the wind"
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  val rebound = { (m:CanvasMover) =>
    if (m.loc.x >= (canvas.width - m.radius / 2)) {
      m.vel = Vector2D(-m.vel.x, m.vel.y)
      m.loc = Vector2D(canvas.width - m.radius / 2, m.loc.y)
    } else if (m.loc.x <= m.radius/2) {
      m.vel = Vector2D(-m.vel.x, m.vel.y)
      m.loc = Vector2D(m.radius / 2, m.loc.y)
    }
    if (m.loc.y >= (canvas.height - m.radius / 2)) {
      m.vel = Vector2D(m.vel.x, -m.vel.y)
      m.loc = Vector2D(m.loc.x, canvas.height - m.radius / 2)
    }
  }

  val start = Vector(10, 10)
  val wind = Vector(0.05, 0)
  // val gravity = Vector(0, 0.2)
  // val force = wind + gravity

  val movers = (1 to 20).map(i => {
    val m = new CanvasMover(start, Vector.zero, canvas)
    m.mass = i * 0.1
    m.reRadius
    m.relocateF = Some(rebound)
    m.setForceF( (m:Mover) => {
      Vector(0, 0.2*m.mass) + wind
    })
    m})

  def draw = {
    ctx.clearRect(0, 0, width, height)
    movers.foreach(m => { m.update; m.draw(ctx) })
  }
}