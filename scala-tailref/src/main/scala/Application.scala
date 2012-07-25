import java.io.File
import org.apache.commons.io.input.Tailer

object Application {

  def main(args: Array[String]) = {
    if (args.size > 0) {
      val filename = args(0)
      val tailer = new Tailer(new File(filename), new FileListener(), 1000, true)
      val thread = new Thread(tailer)
      //thread.setDaemon(true);
      thread.start
      println("Running - tailing log file " + filename)
      println("Press <Ctrl+C> to exit")
    }
  }

}

