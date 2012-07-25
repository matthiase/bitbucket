import java.net.URL
import de.l3s.boilerpipe.extractors._

object Distiller {

  def main(args: Array[String]) = {
    val usage = """
      Distill - making sense of unstructured data
      Usage: distill url
    """

    if (args.isEmpty) {
      println(usage)
    }
    else {
      val url = new URL(args(0))
      val extractor = ArticleExtractor.INSTANCE
      val text = extractor.getText(url)
      println(text)
    }
  }

}
