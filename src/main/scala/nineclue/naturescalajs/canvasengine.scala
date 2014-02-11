package nineclue.naturescalajs

import scala.scalajs.js
import org.scalajs.dom
import js.Dynamic.{ global => g }

abstract trait CanvasEngine {
  var handlerID:js.Number = 0
  var active = false
  var interval:Int = 1000 / 30
  val trackMouse = false
  var mx:Double = 0.0
  var my:Double = 0.0

  /* descendents should call newCanvas method with appropriate parameters and
     assign its return value to canvas variable
     also should implement draw method
  */
  val canvas:dom.HTMLCanvasElement
  val description:String
	def draw:Unit

  def activate = {
    if (active) dom.clearInterval(handlerID)
    handlerID = dom.setInterval(() => draw, interval)
    active = true
  }
  def deactivate = {
    if (active) dom.clearInterval(handlerID)
    handlerID = 0
    active = false
  }

  def setActive(v:Boolean) = if (v) activate else deactivate

  def newCanvas(id:String, width:Int = 600, height:Int = 600, itv:Int = 1000/30) = {
    val can = g.document.createElement("canvas").asInstanceOf[dom.HTMLCanvasElement]
    can.id = id
    can.width = width
    can.height = height
    can.style.border = "1px solid gray"
    interval = itv
    can
  }
}
