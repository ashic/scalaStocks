import java.util.Date

import org.scalactic.TolerantNumerics
import org.scalatest.{FlatSpec, Matchers}
import stockTicker.StockPricing

import scala.collection.immutable.Seq



class StockPricingSpec extends FlatSpec with Matchers {

  behavior of classOf[StockPricing].getName

  import TestPriceSource._
  val pricing = new StockPricing()
  import pricing._

  it should "fetch prices from source" in {
    dailyPrices("GOOG") shouldEqual List(10.0, 5.0, 12.0, 11.0)
  }

  it should "fetch returns from source" in {
    returns("GOOG") shouldEqual List(5.0, -7.0, 1.0)
  }

  it should "produce average of returns from source data" in {
    meanReturn("GOOG") shouldBe -(1.0/3) +- 0.001
  }
}

