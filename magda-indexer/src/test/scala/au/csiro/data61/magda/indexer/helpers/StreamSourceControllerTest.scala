package au.csiro.data61.magda.indexer.helpers

import akka.NotUsed
import akka.actor.{ActorRef, ActorSystem}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import au.csiro.data61.magda.model.misc.DataSet
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{AsyncFlatSpec, _}

import scala.concurrent.{ExecutionContextExecutor, Future}

class StreamSourceControllerTest extends AsyncFlatSpec with Matchers
  with BeforeAndAfterAll with BeforeAndAfterEach {

  implicit val system: ActorSystem = ActorSystem("StreamSourceControllerTest")
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val config: Config = ConfigFactory.load()

  var ssc: StreamSourceController = None.orNull
  var actorRef: ActorRef = None.orNull
  var source: Source[DataSet, NotUsed] = None.orNull

  private val dataSet1 =
    DataSet(identifier = "d1", catalog = Some("c"), quality = 1.0D, score = Some(1.0F))
  private val dataSet2 =
    DataSet(identifier = "d2", catalog = Some("c"), quality = 1.0D, score = Some(1.0F))
  private val dataSet3 =
    DataSet(identifier = "d3", catalog = Some("c"), quality = 1.0D, score = Some(1.0F))

  private val dataSets: Seq[DataSet] = List(dataSet1, dataSet2, dataSet3)

  override def beforeEach(): Unit = {
    super.beforeEach()
    ssc = new StreamSourceController(None.orNull, None.orNull)
    val (actorRef1, source1) = ssc.refAndSource
    actorRef = actorRef1
    source = source1
  }

  private def futureAssert(actualF: Future[Seq[DataSet]],
                           expected: Seq[DataSet]): Future[Assertion] = {
    actualF.map(actual => {
      // This block might not be executed at all for some programming error.
      // Print out for checking when debugging.
      println(actual)
      actual shouldEqual expected
    })
  }

  "The stream source controller" should "fill the source after the stream is alive" in {
      // take(dataSets.size) in order to terminate the stream automatically
      // so that actualDataSetsF can complete.
      val actualDataSetsF: Future[Seq[DataSet]] = source.take(dataSets.size).runWith(Sink.seq)

      // Fill the source.
      dataSets.foreach(dataSet => actorRef ! dataSet)

      futureAssert(actualDataSetsF, dataSets)
  }

  it should "fill the source before the stream is alive" in {
      dataSets.foreach(dataSet => actorRef ! dataSet)
      val actualDataSetsF: Future[Seq[DataSet]] = source.take(dataSets.size).runWith(Sink.seq)
      futureAssert(actualDataSetsF, dataSets)
    }

  it should "fill the source before and after the stream is alive" in {
      dataSets.foreach(dataSet => actorRef ! dataSet)
      val actualDataSetsF: Future[Seq[DataSet]] = source.take(2*dataSets.size).runWith(Sink.seq)
      dataSets.foreach(dataSet => actorRef ! dataSet)
      futureAssert(actualDataSetsF, dataSets ++ dataSets)
    }

  it should "be able to terminate the stream explicitly" in {
    // Fill the source.
    dataSets.foreach(dataSet => actorRef ! dataSet)

    // Run the stream.
    val actualDataSetsF: Future[Seq[DataSet]] = source.runWith(Sink.seq)

    // Fill more.
    dataSets.foreach(dataSet => actorRef ! dataSet)

    // Give some time for actualDataSetsF to run. The future will complete when the stream
    // is terminated by calling ssc.terminate().
    // If not sleeping here, terminate() might be called before the stream gets chance to run,
    // resulting in an empty actualDataSets next.
    Thread.sleep(500)

    // No more data to fill the source, terminate the stream so that
    // actualDataSetsF then resultF can complete
    ssc.terminate()
    futureAssert(actualDataSetsF, dataSets ++ dataSets)
  }
}