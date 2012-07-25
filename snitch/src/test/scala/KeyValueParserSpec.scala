import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class KeyValueParserSpec extends Spec with ShouldMatchers {
  val input = """site="coedmagazine.com" siteuuid="34ea90c288eaac7f7c8241e739ff3f8409195f84fe845d6fcb557ea29cbe0388" pageuuid="42388b757b3202a313d25d005d8d27a1" href="http://coedmagazine.com/2011/02/03/100-busty-asian-bunnies-bring-in-the-year-of-the-rabbit-photos/" referrer="http://www.heavy.com/action/girls/2011/08/the-20-hottest-photos-of-jessica-jane-clement/12/" user="CDD3-LSx0TQ4WA8O6BBOB.Q8B2-LSd0J1S0HR62UA8M.20120202T210027""""
  describe("A KeyValueParser") {
    it("should be able to parse a string of key/value pairs") {
      val properties = KeyValueParser(input) match {
        case KeyValueParser.Success(result, _) => result
        case _ => null
      }
      properties should not be (null)
      properties.size should equal (6)
    }
  }
}
