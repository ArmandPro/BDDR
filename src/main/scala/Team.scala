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
    println("regen:", creature.regen)
    println("health:", creature.hp)
    println("armor:", creature.armor)
    println("\n")

    for (i <- 0 until number) {
      val crea = classe.newInstance()
      teamMembers += CommonCreature.add(crea)
    }
  }


  //return an array of all the vertices
  def vertices(): ArrayBuffer[(VertexId, Int)] = {
    val result = teamMembers.zipWithIndex.map{case (creature, index) => (index.toLong, creature)}

    result
  }

  //return an array of all the edges
  def edges(): ArrayBuffer[Edge[Int]] = {
    val length = teamMembers.length
    val result =
      for (compt1 <- 0 until length; compt2 <- (compt1 + 1) until length)
        yield Edge(compt1.toLong, compt2.toLong, 1)

    result.to[mutable.ArrayBuffer]
  }


}
