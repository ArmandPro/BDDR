/**
  * Created by Etudes on 24/11/2017.
  */


//imports
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import scala.util.parsing.json._
import org.apache.spark.graphx._
// To make some of the examples work we will also need RDD
import org.apache.spark.rdd.RDD
import org.apache.spark.graphx.{Graph, VertexId}
import org.apache.spark.graphx.util.GraphGenerators
import scala.collection.JavaConversions._
import com.google.gson.Gson
import org.json4s.JsonAST._




object bdd extends App {



  //create sqlContext
  val conf = new SparkConf().setAppName("Simple Application1").setMaster("local[*]")  // local mode
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)


}
