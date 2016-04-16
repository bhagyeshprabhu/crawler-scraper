package init

import com.google.inject.{Guice, AbstractModule}
import controllers.CrawlerController
import net.codingwell.scalaguice.InjectorExtensions._
import net.codingwell.scalaguice.ScalaModule
import play.api.libs.ws.WSClient
import play.api.libs.ws.ning.NingWSClient

/**
* The Guice wrapper for initializing and closing the crawler.
*/
object GuiceWrapper {
  lazy val inj = Guice.createInjector(new DependencyModule)

  def initCrawler: CrawlerController = inj.instance[CrawlerController]

  def shutdownCrawler = inj.instance[WSClient].close()
}

/**
 * The Dependency module for bindng the WSClient implementation.
 */
class DependencyModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[WSClient].toInstance(NingWSClient())
  }
}