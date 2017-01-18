package stockTicker

import java.text.SimpleDateFormat
import java.util.Date

case class StockPrice(ticker:String, date: Date, closingPrice: Double)

object StockPrice {

  private val dateFormat = new SimpleDateFormat("yyyy-MM-dd")

  def apply(ticker: String, csv: String) : StockPrice = {
    val parts = csv.split(',')
    StockPrice(ticker, dateFormat.parse(parts(0)), parts(6).toDouble)
  }

}
