package stockTicker

import java.io.{BufferedReader, InputStreamReader}
import java.time.LocalDate

import com.twitter.bijection.Conversion.asMethod
import com.twitter.finagle.http.Response
import com.twitter.finagle.service.{Backoff, RetryBudget}
import com.twitter.finagle.{Http, http}
import com.twitter.util.TimeConversions._
import com.twitter.util.{Await, Future}

import scala.collection.immutable.Seq

object Yahoo {


  private def rootUri = "real-chart.finance.yahoo.com"

  def pricesURL(businessDate : java.time.LocalDate, ticker: String) : String = {
    val lastYear = businessDate.minusYears(1)
    val url =f"http://$rootUri/table.csv?s=$ticker&a=${lastYear.getMonthValue}&b=${lastYear.getDayOfMonth}&c=${lastYear.getYear}&d=${businessDate.getMonthValue}&e=${businessDate.getDayOfMonth}&f=${businessDate.getYear}&g=d&ignore=.csv"
    url
  }

  private val budget: RetryBudget = RetryBudget(
    ttl = 10.seconds,
    minRetriesPerSec = 5,
    percentCanRetry = 0.1
  )

  private val client = Http.client
    .withRetryBudget(budget)
    .withRetryBackoff(Backoff.const(10 seconds))
    .newService(s"$rootUri:80")

  implicit val priceSource = new PriceSource[StockPrice] {
    override def fetchPrices(ticker: String) : Seq[StockPrice]  = {
      val request = http.Request(http.Method.Get, pricesURL(LocalDate.now, ticker))
      request.host = rootUri
      val response = client(request).as[Future[Response]]
      Await.result(response map { res =>
        val reader = new BufferedReader(new InputStreamReader(res.getInputStream))
        reader.readLine()
        Stream.continually(reader.readLine())
          .takeWhile(s=> s!= null && !s.isEmpty)
          .map { StockPrice(ticker, _) }
      })
    }
  }
}

