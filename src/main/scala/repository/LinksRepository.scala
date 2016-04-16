package repository

import scala.collection.mutable

/**
 * The LinksRepository for storage and management of links.
 */
class LinksRepository {

  val links = new mutable.LinkedHashSet[String]

  var index = 0

  def getNextLink: Option[String] = {
    val link = links.toList.lift(index)
    index += 1
    link
  }

  def getLinks: List[String] = links.toList

  def size: Int = links.size

  def addLink(link: String) = links.add(link)

  def addLinks(links: List[String]) = this.links ++= links

}