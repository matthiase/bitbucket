import scala.util.parsing.combinator._

object SyslogParser extends RegexParsers {
  case class Event(date: String, time: String, host: String, program: String, pid: String, message: String)

  override def skipWhitespace = false

  def apply(input: String): ParseResult[Event] = {
    parse(event, input) 
  }

  private def event: Parser[Event] = {
    date ~ time ~ host ~ program ~ pid ~ message ^^ {
      case date ~ time ~ host ~ program ~ pid ~ message => Event(date, time, host, program, pid, message)
    }
  }

  val space = """\s+""".r
  val date = """\S{3}\s+\d+""".r <~ space
  val time = """\d+:\d+:\d+""".r <~ space
  val host = regex("""[-\w\.\@:]+""".r) <~ space
  val program = regex("""\w+""".r)
  val pid = "[" ~> regex("""\d+""".r) <~ "]" <~ ":"
  val message = space ~> """.+""".r

}
