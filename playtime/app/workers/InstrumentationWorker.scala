package workers

import akka.actor._
import java.net.URL
import de.l3s.boilerpipe.extractors._
import model.Instrumentation

case class InstrumentationRequest(url: String)

class InstrumentationWorker extends Actor with ActorLogging {
  def receive = {
    case InstrumentationRequest(url) => 
      log.info("Received request for: " + url)
      val urlObj = new URL(url)
      val extractor = ArticleExtractor.INSTANCE
      val text = extractor.getText(urlObj)
      Instrumentation.insert(url, "text", text)
      log.info("PERSISTING:\n" + text)
      log.info("Completed request for: " + url)
  }
}
