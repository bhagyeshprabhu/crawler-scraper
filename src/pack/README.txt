System Requirement: Java 1.7+

This application is responsible for crawling a website, scraping the links, storing them in the repository and repeat the same for next links sequentially, until the limit is reached if specified else it goes on till the last url is crawled.

To execute the application execute file 'crawl' by:
crawl {url} {limit}

For e.g.
crawl http://www.google.com 100

Here limit is optional. It can be omitted. In this case all the URLs are crawled until no new URL is fetched.