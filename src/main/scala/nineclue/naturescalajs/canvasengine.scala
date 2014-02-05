package nineclue.naturescalajs

import scala.scalajs.js
import org.scalajs.dom
import js.Dynamic.{ global => g }

abstract trait CanvasEngine {
  var handlerID:js.Number = 0
  var active = false

  /* descendents should call newCanvas method with appropriate parameters and
     assign its return value to canvas variable
     also should implement draw method
  */
  val canvas:dom.HTMLCanvasElement
  val description:String
	def draw:Unit

  def activate(fps:Int = 30):Unit = {
    if (active) dom.clearInterval(handlerID)
    handlerID = dom.setInterval(() => draw, 1000 / fps)
    active = true
  }
  def deactivate = {
    if (active) dom.clearInterval(handlerID)
    handlerID = 0
    active = false
  }

  def setActive(v:Boolean, fps:Int = 30) = if (v) activate(fps) else deactivate

  def newCanvas(id:String, width:Int = 600, height:Int = 600) = {
    val can = g.document.createElement("canvas").asInstanceOf[dom.HTMLCanvasElement]
    can.id = id
    can.width = width
    can.height = height
    can.style.border = "1px solid gray"
    can
  }
}
