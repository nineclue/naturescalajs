package nineclue.naturescalajs

import scala.scalajs.js
import org.scalajs.dom
import js.Dynamic.{ global => g }

object Naturescalajs {
  type Page = (String, List[CanvasEngine])
  val introduction:List[CanvasEngine] = List(new RandomWalk, new NoiseWalk,
    new RandomGraph, new NoiseGraph, new TwoDNoise)
  val vector:List[CanvasEngine] = List(new BouncingBall, new VectorMagnitude,
    new Motion101)
  val forces:List[CanvasEngine] = List(new Forces0)
  val examples:List[Page] = List(("Introduction", introduction),
    ("Chapter 1. Vector", vector), ("Chapter 2. Force", forces))

  def main(): Unit = {
    val pages = g.document.createElement("ul")
    pages.id = "demos"
    examples.foreach { case (pTitle, cs) =>
      val page = g.document.createElement("li")
      page.className = "chapter"
      page.innerHTML = pTitle
      // page.appendChild(g.document.createElement("br"))
      val button = g.document.createElement("button").asInstanceOf[dom.HTMLButtonElement]
      button.innerHTML = "open"
      page.appendChild(button)
      val demos = g.document.createElement("div")
      demos.id = "demos"
      page.appendChild(demos)
      button.onclick = { (e:dom.MouseEvent) => {
        if (demos.children.asInstanceOf[js.Array[js.Any]].length == (0:js.Number)) {
          addDemos(demos.asInstanceOf[dom.HTMLElement], cs)
        }
        val display =
          if (button.innerHTML == ("open":js.String)) {
            button.innerHTML = "close"
            "block"
          } else {
            button.innerHTML = "open"
            "none"
          }
        demos.children.asInstanceOf[js.Array[dom.HTMLElement]].foreach { _.style.display = display }
        ():js.Any
      } }:js.Function1[dom.MouseEvent, js.Any]
      pages.appendChild(page)
    }
    g.document.getElementById("playground").appendChild(pages)
    // addDemos(g.document.getElementById("playground"), pages)
	}

  def addDemos(parent:dom.HTMLElement, demos:List[CanvasEngine]) = {
    val items = g.document.createElement("ul")
    demos.foreach { obj =>
      val item = g.document.createElement("li")
      item.id = obj.getClass.getName
      item.innerHTML = obj.canvas.id
      item.style.color = "red"
      item.onclick = { (e:dom.MouseEvent) => {
        if (obj.active) {
          item.style.color = "red"
          obj.deactivate
        } else {
          item.style.color = "green"
          obj.activate
        }
      } }
      if (obj.trackMouse) {
        obj.canvas.onmousemove = { (e:dom.MouseEvent) => {
          val bound = obj.canvas.getBoundingClientRect
          obj.mx = e.clientX - bound.left
          obj.my = e.clientY - bound.top
          ():js.Any
        }}
      }
      item.appendChild(g.document.createElement("br"))
      item.appendChild(obj.canvas)
      items.appendChild(item)
    }
    parent.appendChild(items.asInstanceOf[dom.HTMLElement])
  }
}
