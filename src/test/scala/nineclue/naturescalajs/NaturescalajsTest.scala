package nineclue.naturescalajs

import scala.scalajs.js
import js.Dynamic.{ global => g }
import scala.scalajs.test.JasmineTest

object NaturescalajsTest extends JasmineTest {

  describe("Naturescalajs") {

  	/*
    it("should implement square()") {
      import Naturescalajs._

      expect(square(0)).toBe(0)
      expect(square(4)).toBe(16)
      expect(square(-5)).toBe(25)
    }
    */

    it("map function") {
    	import Utility.map

    	expect(map(-1, 1, -100, 100)(0.5)).toBe(50)

    	def cal = map(1, 30, 0, 640)_
    	expect(cal(1)).toBe(0)
    	expect(cal(30)).toBe(640)
    	expect(cal(15.5)).toBe(320)

    	expect(map(0, 6, 0, 12)(2)).toBe(4)
    }
  }
}
