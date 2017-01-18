import stockTicker.{StockPrice, Yahoo}

object CachedApp extends App {

  val cache = new Cache[String, List[StockPrice]](ticker =>{
    println (s"Fetching $ticker prices from Yahoo...")
    Yahoo.priceSource.fetchPrices(ticker).toList
  })

  val cachedPricing = new CachedPricing(cache)

  import cachedPricing._

  val program = for {
    gPrices <- dailyPrices("GOOG")
    _ = println(s"Daily Prices for Google: $gPrices")
    gReturns <- returns("GOOG")
    _ = println(s"Daily Returns for Google: $gReturns")
    gAvg <- meanReturn("GOOG")
    _ = println(s"Average Return for Google: $gPrices")
    mPrices <- dailyPrices("MSFT")
    _ = println(s"Daily Prices for MSFT: $mPrices")
    mReturns <- returns("MSFT")
    _ = println(s"Daily Returns for MSFT: $mReturns")
    mAvg <- meanReturn("MSFT")
    _ = println(s"Average Return for MSFT: $mPrices")

    gPrices2 <- dailyPrices("GOOG")
    _ = println(s"Daily Prices for Google (again): $gPrices2")
    gReturns2 <- returns("GOOG")
    _ = println(s"Daily Returns for Google (again): $gReturns2")
    gAvg2 <- meanReturn("GOOG")
    _ = println(s"Average Return for Google (again): $gPrices2")
  } yield ()


  program.run(Map.empty[String, List[StockPrice]]).value

}
