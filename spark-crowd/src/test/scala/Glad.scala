import org.apache.log4j.Logger
import org.apache.log4j.Level
import collection.mutable.Stack
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import com.enriquegrodrigo.spark.crowd.methods.Glad
import com.enriquegrodrigo.spark.crowd.types._
import org.scalatest._
import org.scalatest.fixture
import org.scalactic.TolerantNumerics 
import java.io._

class GladTest extends fixture.FlatSpec with Matchers {

  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  case class FixtureParam(spark: SparkSession)

  implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(1e-4f)

  def withFixture(test: OneArgTest) = {
      val conf = new SparkConf().setAppName("Glad test").setMaster("local[4]")
      val spark = SparkSession.builder().config(conf).getOrCreate() 
      val sc = spark.sparkContext
      spark.sparkContext.setCheckpointDir("checkpoint")
      try super.withFixture(test.toNoArgTest(FixtureParam(spark))) finally {
        sc.stop()
      }
  }

  "Glad" should "obtain the expected result in the first example" in { f => 
    val exampleFile = getClass.getResource("/binary-ann.parquet").getPath
    val spark = f.spark
    import spark.implicits._
    val sc = spark.sparkContext
    val exampleData = spark.read.parquet(exampleFile).as[BinaryAnnotation] 
    val mode = Glad(exampleData)
    val fis = mode.getMu().filter(_.example == 0).collect()(0).value
    assert(fis ===  1.0) 
  }

  it should "obtain the expected result in the second example" in { f => 
    val exampleFile = getClass.getResource("/binary-ann.parquet").getPath
    val spark = f.spark
    import spark.implicits._
    val sc = spark.sparkContext
    val exampleData = spark.read.parquet(exampleFile).as[BinaryAnnotation] 
    val mode = Glad(exampleData)
    val fis = mode.getMu().filter(_.example == 1).collect()(0).value 
    assert(fis ===  1.0) 
  }

  it should "obtain the expected result in the eleventh example" in { f => 
    val exampleFile = getClass.getResource("/binary-ann.parquet").getPath
    val spark = f.spark
    import spark.implicits._
    val sc = spark.sparkContext
    val exampleData = spark.read.parquet(exampleFile).as[BinaryAnnotation] 
    val mode = Glad(exampleData)
    val fis = mode.getMu().filter(_.example == 10).collect()(0).value
    assert(fis ===  0.0) 
  }

  it should "obtain the expected result in the third example" in { f => 
    val exampleFile = getClass.getResource("/binary-ann.parquet").getPath
    val spark = f.spark
    import spark.implicits._
    val sc = spark.sparkContext
    val exampleData = spark.read.parquet(exampleFile).as[BinaryAnnotation] 
    val mode = Glad(exampleData)
    val fis = mode.getMu().filter(_.example == 2).collect()(0).value
    assert(fis ===  1.0) 
  }

  it should "obtain the expected result in the first annotator" in { f => 
    val exampleFile = getClass.getResource("/binary-ann.parquet").getPath
    val spark = f.spark
    import spark.implicits._
    val sc = spark.sparkContext
    val exampleData = spark.read.parquet(exampleFile).as[BinaryAnnotation] 
    val mode = Glad(exampleData)
    val fis = mode.getAnnotatorPrecision()(0)
    assert(fis ===  58.6114) 
  }

  it should "obtain the expected result in logLikelihood" in { f => 
    val exampleFile = getClass.getResource("/binary-ann.parquet").getPath
    val spark = f.spark
    import spark.implicits._
    val sc = spark.sparkContext
    val exampleData = spark.read.parquet(exampleFile).as[BinaryAnnotation] 
    val mode = Glad(exampleData)
    val fis = mode.getLogLikelihood()
    assert(fis ===  1386031.2049) 
  }

  it should "obtain the expected result in first example difficulty" in { f => 
    val exampleFile = getClass.getResource("/binary-ann.parquet").getPath
    val spark = f.spark
    import spark.implicits._
    val sc = spark.sparkContext
    val exampleData = spark.read.parquet(exampleFile).as[BinaryAnnotation] 
    val mode = Glad(exampleData)
    val fis = mode.getInstanceDifficulty().filter(_.example==1).collect()(0).beta
    assert(fis ===  1.2689) 
  }





}
