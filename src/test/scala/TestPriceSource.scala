import java.util.Date

import stockTicker.{PriceSource, StockPrice}

import scala.collection.immutable.Seq

object TestPriceSource {

  implicit val source = new PriceSource[StockPrice] {

    val today = new Date()

    override def fetchPrices(ticker: String): Seq[StockPrice] =
      List(
        StockPrice(ticker, today, 10.0),
        StockPrice(ticker, getPreviousDate(today, 1), 5.0),
        StockPrice(ticker, getPreviousDate(today, 2), 12.0),
        StockPrice(ticker, getPreviousDate(today, 3), 11.0)
      )

    private def getPreviousDate(date: Date, n:Int) =
      new Date(date.getTime() - n* (1000*60*60*24))
  }

}
