import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class FileProcessorSpec extends Spec with ShouldMatchers {

  describe("A FileProcessor") {
    it ("should be able to read a text file") {
      var event: SyslogParser.Event = null
      FileProcessor.run("/home/matthias/sample.log", (line: String) => { 
        event = SyslogParser(line) match {
          case SyslogParser.Success(result, _) => result
          case _ => null
        }
        event should not be (null)
        event.message should not be (null)
      })
    }
  }

}
