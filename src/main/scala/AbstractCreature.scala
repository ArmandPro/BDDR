/**
  * Created by Etudes on 13/12/2017.
  */
import java.util
import org.apache.spark._
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.graphx.{Graph, VertexId}


import scala.collection.mutable.ArrayBuffer


abstract class AbstractCreature(val name : String, val id : Int) {



  var hp : Int

  var hpMAx : Int

  var armor : Int

  val reg: Int = 0

  val damageRed :Int = 0

  val team : String

  val dead : Boolean = false

  def isDead(): Boolean = {
    if (hp <= 0) true
    else false
  }

  def getSide(): String ={
    this.team
  }


  //to delete
  def diceThrow(throwNumber: Int, facesNumber: Int): Int ={

    val random = scala.util.Random

    var sum = 0

    var x = 0

    for ( x <- 1 to throwNumber){
      sum += random.nextInt(facesNumber)+1
    }

    sum
  }

  //genarate a random value of the diceNum roll of facesNum dice
  def dice(diceNum : Int, facesNum : Int) : Int={

    val r = scala.util.Random

    var sum = 0

    var i = 0

    for(i <- 1 to diceNum){

    sum += r.nextInt(facesNum)
    sum += 1

    }

  sum

  }


}
