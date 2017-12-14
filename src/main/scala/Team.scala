/**
  * Created by Etudes on 13/12/2017.
  */


import org.apache.spark.graphx.{Edge, VertexId}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


/////// A MODIFIER
class Team {


  var teamMembers: ArrayBuffer[Int] = ArrayBuffer.empty[Int]

  def add(creature: AbstractCreature, number : Int = 1): Unit = {

    val classe = creature.getClass()

    println(creature.name, number)
    println("regen:", creature.reg)
    println("health:", creature.hp)
    println("armor:", creature.armor)
    println("\n")

    for (i <- 0 until number) {
      val crea = classe.newInstance()
      //teamMembers += StoreCreature.add(crea)
    }
  }

  /*
  def vertices(): ArrayBuffer[(VertexId, Int)] = {
    val result = members.zipWithIndex.map{case (creature, index) => (index.toLong, creature)}

    result
  }

  def edges(): ArrayBuffer[Edge[Int]] = {
    val result = for (i <- 0 until members.length; j <- (i + 1) until members.length) yield Edge(i.toLong, j.toLong, 1)

    result.to[mutable.ArrayBuffer]
  }*/

}
