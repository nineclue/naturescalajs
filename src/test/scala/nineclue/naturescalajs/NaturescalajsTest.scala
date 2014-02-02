package nineclue.naturescalajs

import scala.scalajs.js
import js.Dynamic.{ global => g }
import scala.scalajs.test.JasmineTest

object NaturescalajsTest extends JasmineTest {

  describe("Naturescalajs") {

    it("should implement square()") {
      import Naturescalajs._

      expect(square(0)).toBe(0)
      expect(square(4)).toBe(16)
      expect(square(-5)).toBe(25)
    }
  }
}
