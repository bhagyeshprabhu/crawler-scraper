package controllers

import com.google.inject._
import play.api.Logger
import repository.LinksRepository
import services.crawler.CrawlerService
import services.scraper.ScraperService

/**
 * The Crawler Controller is responsible for crawling a website, scraping the links, storing them in the repository and
 * repeat the same for next links sequentially, until the limit is reached if specified else it goes on till the last url is crawled.
 *
 * @param crawlerService The repository that stores the session.
 * @param scraperService The repository that stores the tariff.
 * @param linksRepository The repository that store the links.
 */
@Singleton
class CrawlerController @Inject() (crawlerService: CrawlerService, scraperService: ScraperService, linksRepository: LinksRepository) {

  /**
   * The method to start the crawling process with the passed url until the limit is reached if specified.
   *
   * @param url The start URL for the crawler.
   * @param limit Limit of links to be fetched while crawling. This is an optional field
   * @return the list of links scraped from the crawled pages.
   */
  def crawl(url: String, limit: Option[Int] = None): List[String] = {

    var link = Option(url)

    while(link.isDefined && (limit.isEmpty || (limit.isDefined && linksRepository.size < limit.get))){
      try {
        val webPageContent = crawlerService.getWebPageContent(link.get)
        val scrapedLinks = scraperService.getHyperLinks(webPageContent)
        linksRepository.addLinks(scrapedLinks)
      }catch{
        case e: Exception => Logger.error(s"Error while fetching URL: $link | Exception: $e", e)
      }finally {
        link = linksRepository.getNextLink
      }
    }

    limit.fold(linksRepository.getLinks)(linksRepository.getLinks.take(_))
  }
}