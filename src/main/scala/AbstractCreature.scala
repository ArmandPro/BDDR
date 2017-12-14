/**
  * Created by Etudes on 13/12/2017.
  */

import java.util
import org.apache.spark._
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.graphx.{Graph, VertexId}


abstract class AbstractCreature(val name : String) {




  var hp : Int
  val hpMax : Int
  var armor : Int

  var posX : Int = scala.util.Random.nextInt(101)
  var posY : Int = scala.util.Random.nextInt(101)
  var posZ : Int = 0

  val regen: Int = 0

  val damageReduction :Int = 0

  val team : String

  var dead : Boolean = false

  def isDead: Boolean = {
    if (hp <= 0) true
    else false
  }

  def getSide: String ={
    this.team
  }


  def refresh(): Unit ={
    hp = math.min(this.hp+regen, hpMax)

  }


  //generate a random value of the diceNum roll of facesNum dice
  def dice(diceNum : Int, facesNum : Int) : Int={

    val r = scala.util.Random
    var sum = 0
    var compt = 0

    for(compt <- 1 to diceNum){
    sum += r.nextInt(facesNum)
    sum += 1
    }
  sum
  }


  def naiveAttack(id: VertexId, graph: Graph[Int, Int], store: Broadcast[CommonCreature.type]) : Unit =
  {

    var played = false

    val result = findLowestHealthEnemy(id, graph, store)

    if (result._2 != -1)
      played = melee(this.baseMeleeAttack, store.value.get(result._2))

    if (!played) {
      println(s"\t$name can't do anything\n")
    }
  }


  def findClosestEnemy(id: VertexId, graph: Graph[Int, Int], store: Broadcast[CommonCreature.type]) : (VertexId, Int) = {
    val temp = graph.aggregateMessages[(VertexId, Int, Int)](
      edge => {
        val isEnemy = edge.toEdgeTriplet.attr == 0

        if ((edge.srcId == id) && isEnemy) {
          val key = edge.dstAttr
          val creature = store.value.getAbstractCreature(key)

          if (!creature.isDead()) {
            edge.sendToSrc((edge.dstId, key, creature.hp))
          }
        }
      },
      (a, b) => if (b._3 > a._3) a else b)

    val resultAggregate = temp.collect()

    if (resultAggregate.length == 0) {

      return (-1, -1)
    }

    val result = resultAggregate(0)._2
    return (result._1, result._2)
  }


  def  isDead() : Boolean = {
     if(hp<0)
       this.dead = true

    dead
  }



}
