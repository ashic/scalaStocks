package stockTicker

import scala.collection.immutable.Seq


class StockPricing(implicit priceSource: PriceSource[StockPrice]) {

  /* 1 - 1 year historic prices given a ticker */
  def dailyPrices(ticker: String) : List[Double] =
    priceSource.fetchPrices(ticker) map (_.closingPrice) toList

  /* 2- daily returns, where return = ( Price_Today – Price_Yesterday)/Price_Yesterday */
  def returns(ticker:String) : Seq[Double] = {
    val prices = priceSource.fetchPrices(ticker) map (_.closingPrice)
    val shifted = prices.tail
    val pairs = prices.zip(shifted)

    pairs map {
      case (t, y) => t - y
    }
  }

  /* 3 – 1 year mean returns */
  def meanReturn(ticker:String): Double =
    returns(ticker).foldLeft((0.0, 1)) ((acc, i) => ((acc._1 + (i - acc._1) / acc._2), acc._2 + 1))._1
}



