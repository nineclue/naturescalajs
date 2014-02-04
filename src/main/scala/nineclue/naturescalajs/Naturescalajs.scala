package nineclue.naturescalajs

object Naturescalajs {
  def main(): Unit = {
  	new RandomDraw("playground", 600, 300)
  	// (1 to 100).foreach(i => println(s"$i : ${Utility.noise(i)}"))
  	new NoiseDraw("playground", 600, 300)
	}

}
