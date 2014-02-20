package nineclue.naturescalajs

import util.Random
import org.scalajs.dom

class SpiralDraw(val width:Int = 600, val height:Int = 400) extends CanvasEngine {
  val canvas:dom.HTMLCanvasElement = newCanvas("SpiralDraw", width, height)
  val description = "Chap.3 Drawing circles in spiral way"
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  ctx.fillStyle = "green"
  var theta = 0.0
  var r = 0.0

  def draw = {
    val x = r * Math.cos(theta)
    val y = r * Math.sin(theta)

    ctx.beginPath
    ctx.arc(x + width/2, y + height/2, 5, 0, Math.PI * 2)
    // c.stroke
    ctx.fill

    theta += 0.02
    r += 0.05
  }
}

class SpaceShip(val width:Int = 600, val height:Int = 400) extends CanvasEngine {
  val canvas:dom.HTMLCanvasElement = newCanvas("SpaceShip", width, height)
  val description = "Chap.3 SpaceShip"
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  override val trackKey = true

  val ship = new CanvasMover(Vector(width/2, height/2), Vector.zero, canvas)
  val size = 10
  canvas.style.background = "black"
  // ctx.fillStyle = "black"
  ctx.strokeStyle = "white"

  ship.draw = { (c:dom.CanvasRenderingContext2D) =>
    c.beginPath
    c.moveTo(ship.loc.x, ship.loc.y)
    c.lineTo(ship.loc.x + Math.sin(ship.ang + 120 * Math.PI / 180) * size,
      ship.loc.y + Math.cos(ship.ang + 120 * Math.PI / 180) * size)
    c.lineTo(ship.loc.x + Math.sin(ship.ang) * size,
      ship.loc.y + Math.cos(ship.ang) * size)
    c.lineTo(ship.loc.x + Math.sin(ship.ang + 240 * Math.PI / 180) * size,
      ship.loc.y + Math.cos(ship.ang + 240 * Math.PI / 180) * size)
    c.closePath
    c.stroke
  }

  ship.aAccelF = Some({ (_:Mover) =>
    (if (keys.contains(37)) 0.01 else 0.0) +
      (if (keys.contains(39)) -0.01 else 0.0)
  })

  ship.forceF = Some({ (_:Mover) =>
    Vector(Math.sin(ship.ang), Math.cos(ship.ang)) *
     (if (keys.contains(38)) 1 else 0)
     // no backflow in spaceship
     // ((if (keys.contains(38)) 1 else 0) + (if (keys.contains(40)) -1 else 0))
  })

  def draw = {
    ctx.clearRect(0, 0, width, height)
    ship.update
    ship.draw(ctx)
  }
}