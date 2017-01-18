package stockTicker

import scala.collection.immutable.Seq

trait PriceSource[A] {
  def fetchPrices(ticker: String) : Seq[A]
}
