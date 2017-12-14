/**
  * Created by Etudes on 24/11/2017.
  */


//imports
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext





object bdd extends App {



  //create sqlContext
  val conf = new SparkConf().setAppName("Simple Application1").setMaster("local[*]")  // local mode
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)


}
