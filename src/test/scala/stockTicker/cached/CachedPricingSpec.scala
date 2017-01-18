
import cats.data.State
import org.scalatest.{FlatSpec, Matchers}
import stockTicker.StockPrice

class CachedPricingSpec extends FlatSpec with Matchers {

  behavior of classOf[CachedPricing].getName

  val cache = new Cache[String, List[StockPrice]](TestPriceSource.source.fetchPrices(_).toList)
  val pricing = new CachedPricing(cache)

  import pricing._

  it should "fetch prices from cache" in  {
    $(dailyPrices("GOOG")) shouldEqual List(10.0, 5.0, 12.0, 11.0)
  }

  it should "fetch returns from source" in {
    $(returns("GOOG")) shouldEqual List(5.0, -7.0, 1.0)
  }

  it should "produce average of returns from source data" in {
    $(meanReturn("GOOG")) shouldBe -(1.0/3) +- 0.001
  }

  private def $[B, C](s:State[Map[String, B], C]) = s.runA(Map.empty[String, B]).value

}
