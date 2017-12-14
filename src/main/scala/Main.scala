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
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.{SparkConf, SparkContext, graphx}
import org.apache.spark.util.LongAccumulator
import scala.collection.mutable.ArrayBuffer





object Main extends App {



  //create sqlContext
  val conf = new SparkConf()
    .setAppName("fight")
    .setMaster("local[1]")

  val sc = new SparkContext(conf)
  sc.setLogLevel("ERROR")

  //create team of the allies of coalition
  var coalition = new Team()
  coalition.add(new BestiaireSolar())

  //create team of the enemy
  var enemy = new Team()
  enemy.add(new HalfOrcBarbarian() ,4)
  enemy.add(new HalfOrcBrutalWarLord())
  enemy.add(new HalfOrcWorgRider(),9)

  //stop condition
  //All orc eliminated OR
  //Accumulator
  var coalitionDead: LongAccumulator = sc.longAccumulator("counterCoalition")
  var enemyDead: LongAccumulator = sc.longAccumulator("counterEnemy")

  val allies_len = coalition.teamMembers.length
  val offset = enemy.teamMembers.length

  var vertices = coalition.vertices()
  vertices ++= enemy.vertices().map(e => (offset + e._1, e._2))

  val coalitionEdges = coalition.edges()
  val enemiesEdges = enemy.edges().map(e => Edge(offset + e.srcId, offset + e.dstId, e.attr))
  // Joins allies and enemies
  val inBetweenEdges = for (i <- 0 until allies_len; j <- 0 until enemy.teamMembers.length) yield Edge(i.toLong, (offset + j).toLong, 0)


  //generate all the edges in the both direction
  var edges = coalitionEdges ++ inverseEdges(coalitionEdges)
  edges ++= enemiesEdges ++ inverseEdges(enemiesEdges)
  edges ++= inBetweenEdges ++ inverseEdges(inBetweenEdges.to[ArrayBuffer])

  //reverse the direction of the edges
  def inverseEdges(edges: ArrayBuffer[Edge[Int]]): ArrayBuffer[Edge[Int]] = {
    return edges.map(e => Edge(e.dstId, e.srcId, e.attr))
  }



  var commonCreature = sc.broadcast(CommonCreature)


  var pVertices = sc.parallelize(vertices)
  var pEdges = sc.parallelize(edges)

  val graphN = Graph(pVertices, pEdges, coalition.teamMembers(0))

  val graphNplus1 = graphN.vertices.collect().map(_._1)


  val continue = true

  while(continue){

    graphNplus1.map(id => {
      var key = graphN.vertices.filter(_._1 == id).first()._2
      var c = commonCreature.value.getAbstractCreature(key)

      c.update()
      if (c.deadSince<1) {
        c.naiveAttack(id, graphN, commonCreature)
      }
      else {
        val name = c.name
        val deadSince = c.deadSince
        println(s"$name $id died $deadSince turns ago\n")
        if (c.getSide() == "Ally")
          alliesDead.add(1)
        else
          enemiesDead.add(1)
      }
    }



  }




}

