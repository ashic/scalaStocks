import cats.data.State

class Cache[K, V](origin: K => V) {
  import State._

  def fetch(key:K) : State[Map[K, V], V] = for {
    m <- get[Map[K, V]]
    v = m.getOrElse(key, origin(key))
    _ <- set[Map[K, V]](m + (key -> v))
  } yield(v)

}
