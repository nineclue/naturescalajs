package nineclue.naturescalajs

import scala.scalajs.js
import org.scalajs.dom
import js.Dynamic.{ global => g }

object Naturescalajs {
  val examples:List[CanvasEngine] = List(new RandomWalk, new RandomGraph, new NoiseGraph)

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
          obj.activate(30)
        }
      }
      item.appendChild(g.document.createElement("br"))
      item.appendChild(obj.canvas)
      demos.appendChild(item)
    }
    g.document.getElementById("playground").appendChild(demos)
	}

}
