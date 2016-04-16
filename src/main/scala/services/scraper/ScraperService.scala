package services.scraper

import com.google.inject.{ImplementedBy, Singleton}
import org.jsoup.Jsoup

/**
 * The Scraper service which parsed the web page content and scrape the required data.
 */
@ImplementedBy(classOf[JSoupScraperService])
trait ScraperService {

  /**
   * Scraps the hyperlinks from the passed web page content.
   *
   * @param webPageContent The URL to be crawled.
   * @return the content of the page.
   */
  def getHyperLinks(webPageContent: String): List[String]
}

/**
 * The JSoup implementation of Scraper Service.
 */
@Singleton
class JSoupScraperService extends ScraperService {

  override def getHyperLinks(webPageContent: String): List[String] = {
    import scala.collection.JavaConversions._

    Jsoup.parse(webPageContent).select("a[href]").toList.map(_.absUrl("href")).filter(!_.isEmpty)
  }
}