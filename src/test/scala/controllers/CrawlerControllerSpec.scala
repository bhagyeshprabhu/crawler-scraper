package controllers

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import repository.LinksRepository
import services.crawler.CrawlerService
import services.scraper.JSoupScraperService

@RunWith(classOf[JUnitRunner])
class CrawlerControllerSpec extends Specification with Mockito {

  val webPage1 = """<a href="http://www.youtube.com"></a>"""
  val webPage2 = """<a href="http://www.facebook.com"></a><a href="http://www.rediff.com"></a><a href="http://www.hotmail.com"></a>"""
  val webPage3 = """<a href="http://www.rediff.com"></a><a href="http://www.youtube.com"></a>"""
  val webPage4 = """<a href="http://www.hotmail.com"></a><a href="http://www.rediff.com"></a>"""
  val webPage5 = """<a href="http://www.youtube.com"></a><a href="http://www.rediff.com"></a>"""

  val crawlerMock = mock[CrawlerService]
  crawlerMock.getWebPageContent("http://www.google.com") returns webPage1
  crawlerMock.getWebPageContent("http://www.rediff.com") returns webPage2
  crawlerMock.getWebPageContent("http://www.hotmail.com") returns webPage3
  crawlerMock.getWebPageContent("http://www.youtube.com") returns webPage4
  crawlerMock.getWebPageContent("http://www.facebook.com") returns webPage5

  "CrawlerController" should {
    "fetch links till the specified limit if it is specified" in {

      val controller = new CrawlerController(crawlerMock, new JSoupScraperService, new LinksRepository)
      controller.crawl("http://www.google.com", Some(3)) must equalTo(List("http://www.youtube.com", "http://www.hotmail.com", "http://www.rediff.com"))
    }

    "fetch links till all URLs are crawled if limit is not specified" in {

      val controller = new CrawlerController(crawlerMock, new JSoupScraperService, new LinksRepository)
      controller.crawl("http://www.google.com") must equalTo(List("http://www.youtube.com", "http://www.hotmail.com", "http://www.rediff.com", "http://www.facebook.com"))
    }
  }
}
