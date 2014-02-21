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

// it's confusing, using 2d vector as convenient storage and calculator
// I'd rather use tuple, but this is simple port
class Oscillator(location:Vector2D, velocity:Vector2D, val amplitude:Vector2D) extends Mover(location, velocity) {
  /* var noisex = 0.0
  var noisey = 100000.0

  def remap = Utility.map(Double.MinValue, Double.MaxValue, -0.01, 0.01)_
  forceF = Some( (_:Mover) => {
    noisex += 0.01
    noisey += 0.01
    Vector(remap(Utility.noise(noisex)), remap(Utility.noise(noisey)))
  }) */

  def draw(c:dom.CanvasRenderingContext2D) = {
    val x = Math.sin(loc.x)*amplitude.x
    val y = Math.sin(loc.y)*amplitude.y
    c.beginPath
    c.moveTo(0,0)
    c.lineTo(x, y)
    c.closePath
    c.stroke
    c.beginPath
    c.arc(x, y, 20, 0, Math.PI*2)
    c.stroke
    c.fill
  }
}

class Oscillators(val width:Int = 600, val height:Int = 400) extends CanvasEngine {
  val canvas:dom.HTMLCanvasElement = newCanvas("Oscillators", width, height)
  val description = "Chap.3 Oscillators"
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  ctx.strokeStyle = "black"
  ctx.fillStyle = "gray"

  /* val amplitude = 200
  var cycle = 60
  val remap = Utility.map(0, cycle, 0, Math.PI*2)_

  def draw = {
    ctx.clearRect(0,0,width,height)

    val x = amplitude * Math.cos(remap(cycle))
    ctx.save
    ctx.translate(width/2, height/2)
    ctx.beginPath
    ctx.moveTo(0, 0)
    ctx.lineTo(x, 0)
    ctx.arc(x, 0, 30, 0, Math.PI*2)
    ctx.stroke
    ctx.fill
    ctx.restore
    cycle += 1
  }
  */

  val os = (1 to 10).map( (_:Int) => {
    val vel = Vector(Utility.random(-0.05, 0.05), Utility.random(-0.05, 0.05))
    val amp = Vector(util.Random.nextInt(width/2), util.Random.nextInt(height/2))
    new Oscillator(Vector.zero, vel, amp) })

  def draw = {
    ctx.clearRect(0,0,width,height)
    ctx.save
    ctx.translate(width/2, height/2)

    os.foreach( o => {
      o.update
      o.draw(ctx)
    })
    ctx.restore
  }
}

class ComplexWave(val width:Int=600, val height:Int=400) extends CanvasEngine {
  implicit class IntWithRad(d:Int) {
    def toRadian:Double = d * Math.PI / 180
  }
  val canvas:dom.HTMLCanvasElement = newCanvas("ComplexWave", width, height)
  val description = "Chap.3 Comlex Wave"
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  ctx.fillStyle = "rgba(100, 100, 100, 100)"
  ctx.strokeStyle = "black"

  /*
  val xstart = util.Random.nextInt
  var x = xstart
  def ymap = Utility.map(-1, 1, 0, height)_

  def draw = {
    val y = ymap(Math.sin(x.toRadian))
    ctx.beginPath
    ctx.arc(x - xstart, y, 20, 0, Math.PI*2)
    ctx.stroke
    ctx.fill
    x += 1
  }
  */

  var x = 0

  var xval = util.Random.nextInt
  var xnoise = 0.0
  def xmap = Utility.map(-1, 1, 1, 10)_

  var amp = height / 3.0
  var anoise = 10000.0
  def amap = Utility.map(-1, 1, -2, 2)_

  def draw = {
    val y = Math.sin(xval.toRadian) * amp + height / 2

    ctx.beginPath
    ctx.arc(x, y, 20, 0, Math.PI*2)
    ctx.stroke
    ctx.fill

    x += 1
    if (x >= width) { ctx.clearRect(0,0,width,height); x = 0 }
    xval += xmap(Utility.noise(xnoise)).toInt
    amp += amap(Utility.noise(anoise))
    xnoise += 0.01
    anoise += 0.01
  }
}