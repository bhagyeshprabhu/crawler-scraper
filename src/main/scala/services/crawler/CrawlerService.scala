package services.crawler

import com.google.inject.{ImplementedBy, Singleton, Inject}
import play.api.http.Status
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await}
import concurrent.duration._

/**
 * The Crawler service which crawls an URL and fetches the content of the page.
 */
@ImplementedBy(classOf[WsClientCrawlerService])
trait CrawlerService {

  /**
   * Crawls the passed URL and returns the content of the webpage.
   *
   * @param url The URL to be crawled.
   * @return the content of the page.
   */
  def getWebPageContent(url: String): String
}

/**
 * The WSClient implementation of Crawler Service.
 *
 * @param wSClient The client implementation to crawl the webpages.
 */
@Singleton
class WsClientCrawlerService @Inject() (wSClient: WSClient) extends CrawlerService {

  val userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36"
  val reqTimeout = 30

  override def getWebPageContent(url: String): String = {
    Await.result(wSClient.url(url).withHeaders(("User-Agent", userAgent)).get.map { response =>
      response.status match {
        case Status.OK => response.body
        case _ => throw new Exception(s"Got an unexpected Response ${response.status} => ${response.body}")
      }
    }, reqTimeout seconds)
  }
}