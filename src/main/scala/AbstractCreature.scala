/**
  * Created by Etudes on 13/12/2017.
  */


abstract class AbstractCreature(val name : String) {


  var hp : Int
  val hpMax : Int
  var armor : Int

  val regen: Int = 0

  val damageReduction :Int = 0

  val team : String

  val dead : Boolean = false

  def isDead: Boolean = {
    if (hp <= 0) true
    else false
  }

  def getSide: String ={
    this.team
  }


  //generate a random value of the diceNum roll of facesNum dice
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
