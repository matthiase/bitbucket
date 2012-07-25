import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class SyslogParserSpec extends Spec with ShouldMatchers {
  val input = """Feb  2 21:00:10 ms3 execution[29256]: site="coedmagazine.com" siteuuid="34ea90c288eaac7f7c8241e739ff3f8409195f84fe845d6fcb557ea29cbe0388" pageuuid="42388b757b3202a313d25d005d8d27a1" href="http://coedmagazine.com/2011/02/03/100-busty-asian-bunnies-bring-in-the-year-of-the-rabbit-photos/" referrer="http://www.heavy.com/action/girls/2011/08/the-20-hottest-photos-of-jessica-jane-clement/12/" user="CDD3-LSx0TQ4WA8O6BBOB.Q8B2-LSd0J1S0HR62UA8M.20120202T210027""""

  describe("A SyslogParser") {
    it("should be able to parse a Syslog message") {
      val event = SyslogParser(input) match {
        case SyslogParser.Success(result, _) => result
        case _ => null
      }
      event should not be (null)
      event.date should equal ("Feb  2")
      event.time should equal ("21:00:10")
      event.host should equal ("ms3")
      event.program should equal ("execution")
      event.pid should equal ("29256")
      event.message should startWith ("site=")
    }
  }

}
