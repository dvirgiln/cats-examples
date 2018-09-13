/**
  * Created by dave on 31/03/18.
  */
object ExamplesPatters extends App{

  val players= ("David" , 5001) :: ("Pedro", 3000) :: ("Juan", 6000) :: Nil

  val playersWithBigScore = for{
    player <- players
    (name, score) = player
    if(score > 5000)
  } yield name

  playersWithBigScore.foreach(println)

  val lists = List(1, 2, 3) :: List.empty :: List(5, 3) :: Nil

  val listsSizes = for {
    list @ head :: _ <- lists
  } yield list.size

  listsSizes.foreach(println)


  def wordsWithoutOutliers(wordFrequencies: Seq[(String, Int)]): Seq[String] =
    wordFrequencies.collect { case (word, freq) if freq > 3 && freq < 25 => word }

  import scala.io.Source
  import java.net.URL
  def getContent(url: URL): Either[String, Source] =
    if (url.getHost.contains("google"))
      Left("Requested URL is blocked for the good of the people!")
    else
      Right(Source.fromURL(url))


  val aaa=getContent(new URL("http://danielwestheide.com")).left
  val content: Either[Iterator[String], Source] =
    getContent(new URL("http://danielwestheide.com")).left.map(a => Iterator(a))
  // content is the Right containing a Source, as already returned by getContent
  val moreContent: Either[Iterator[String], Source] =
  getContent(new URL("http://google.com")).left.map(Iterator(_))
  // moreContent is a Left containing the msg returned by getContent in an Iterator


  val right1 = Right(1)   : Right[Double, Int]
  val right2 = Right(2)
  val right3 = Right(3)
  val left23 = Left(23.0) : Left[Double, Int]
  val left42 = Left(42.0)




  import scala.util.control.Exception.catching
  def handling[Ex <: Throwable, T](exType: Class[Ex])(block: => T): Either[Ex, T] =
    catching(exType).either(block).asInstanceOf[Either[Ex, T]]


  def partial1[A,B,C](a: A, f: (A,B) => C): B => C = f(a, _)

}
