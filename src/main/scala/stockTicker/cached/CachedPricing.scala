import cats.data.State
import stockTicker.StockPrice

import scala.collection.immutable.Seq

class CachedPricing(cache: Cache[String, List[StockPrice]]) {

  type PriceCache = Map[String, List[StockPrice]]

  /* 1 - 1 year historic prices given a ticker */
  def dailyPrices(ticker: String) : State[PriceCache, List[Double]] = for {
    prices <- cache.fetch(ticker)
  } yield prices.map(_.closingPrice)


  /* 2- daily returns, where return = ( Price_Today – Price_Yesterday)/Price_Yesterday */
  def returns(ticker:String) : State[PriceCache, Seq[Double]] = for {
    prices <- dailyPrices(ticker)
    shifted = prices.tail
    pairs = prices.zip(shifted)
    returns = pairs map {
      case (t, y) => t - y
    } toSeq
  } yield (returns)

  /* 3 – 1 year mean returns */
  def meanReturn(ticker:String): State[PriceCache, Double] = for {
    returns <- returns(ticker)
    mean = returns.foldLeft((0.0, 1)) ((acc, i) => ((acc._1 + (i - acc._1) / acc._2), acc._2 + 1))._1
  } yield mean
}
