import org.apache.commons.io.input.TailerListenerAdapter

class FileListener extends TailerListenerAdapter {
  override def handle(line: String) =  {
    try {
      println(line)
    }
    catch {
      case e => e.printStackTrace
    }
  }
}
