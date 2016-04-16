System Requirement for development:
* Java 1.7+
* Scala 2.11+

This application is responsible for crawling a website, scraping the links, storing them in the repository and repeat the same for next links sequentially, until the limit is reached if specified else it goes on till the last url is crawled.

Execute the application with command --> activator "run http://www.google.com 100"
Where 100 is the limit of URLs to be fetched while crawling. This limit is optional.

Execute the unit test cases with command --> activator test

Create a delivery package with command --> activator pack