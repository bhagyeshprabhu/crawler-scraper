import init.GuiceWrapper
import play.api.Logger

object Crawl extends App{

  val url = args.lift(0)

  val limit = try{
    args.lift(1).map(_.toInt)
  }catch {
    case e: NumberFormatException =>
      Logger.error("Limit should be a number.")
      sys exit
  }

  if(url.isDefined){
    val crawler = GuiceWrapper.initCrawler

    val links = crawler.crawl(url.get, limit)

    Logger.info(s"Fetched links: ${links.size}\n${links.mkString("\n")}")

    GuiceWrapper.shutdownCrawler
  }else{
    Logger.error("Please specify URL as command line argument.")
  }
}
