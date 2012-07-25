import java.io.{File, FileReader, BufferedReader}

object FileProcessor {
  
  def run = (filename: String, func: String => Unit) => {
    val file = new File(filename)
    if (file.exists && file.canRead) {
      val reader = new BufferedReader(new FileReader(file))
      var line: String = null
      do {
        line = reader.readLine
        if (line != null) func(line)
      } while (line != null)
    }
  }

}
