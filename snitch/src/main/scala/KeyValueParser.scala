import scala.util.parsing.combinator._

object KeyValueParser extends JavaTokenParsers {
  
  def apply(input: String) = {
    parse(properties, input)
  }

  val properties = rep(property)

  def property = name ~ value ^^ {
    case name ~ value => (name, value)
  }

  val name: Parser[String] = ident <~ "="
  val value: Parser[String] = """[\w\d\s.:\/\-"]+(?![=\w])""".r

}
