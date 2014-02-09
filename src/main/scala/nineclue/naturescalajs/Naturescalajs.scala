package nineclue.naturescalajs

import scala.scalajs.js
import org.scalajs.dom
import js.Dynamic.{ global => g }

object Naturescalajs {
  /*
  val examples:List[CanvasEngine] = List(new RandomWalk, new NoiseWalk, new RandomGraph, new NoiseGraph, new TwoDNoise)

  def main(): Unit = {
    val demos = g.document.createElement("ul")
    examples.foreach { obj =>
      val item = g.document.createElement("li")
      item.id = obj.getClass.getName
      item.innerHTML = obj.canvas.id
      item.style.color = "red"
      item.onclick = (e:dom.MouseEvent) => {
        if (obj.active) {
          item.style.color = "red"
          obj.deactivate
        } else {
          item.style.color = "green"
          obj.activate
        }
      }
      item.appendChild(g.document.createElement("br"))
      item.appendChild(obj.canvas)
      demos.appendChild(item)
    }
    g.document.getElementById("playground").appendChild(demos)
  }
  */

  type Page = (String, List[CanvasEngine])
  val introduction:List[CanvasEngine] = List(new RandomWalk, new NoiseWalk, 
    new RandomGraph, new NoiseGraph, new TwoDNoise)
  val vector:List[CanvasEngine] = List(new BouncingBall)
  val examples:List[Page] = List(("Introduction", introduction), 
    ("Chapter 1. Vector", vector))

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
      button.onclick = { (e:dom.MouseEvent) => {
        val parent = button.parentNode.asInstanceOf[dom.HTMLElement]
        println(parent.children.length)
          button.innerHTML = "close"
          addDemos(page.asInstanceOf[dom.HTMLElement], cs)
      } }
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
      item.appendChild(g.document.createElement("br"))
      item.appendChild(obj.canvas)
      items.appendChild(item)
    }
    parent.appendChild(items.asInstanceOf[dom.HTMLElement])
  }
}
