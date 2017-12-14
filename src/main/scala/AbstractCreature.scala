

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.graphx.{Graph, VertexId}

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

  var baseMeleeAttack : Array[Int]

  def isDead(): Boolean = {
    if (hp <= 0) true
    else false
  }

  def getSide(): String ={
    this.team
  }


  def refresh(): Unit ={
    hp = math.min(this.hp+regen, hpMax)

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

  def naiveAttack(id: VertexId, graph: Graph[Int, Int], store: Broadcast[CommonCreature.type]) : Unit =
  {

    var played = false

    val result = findClosestEnemy(id, graph, store)

    if (result._2 != -1)
      played = melee(this.baseMeleeAttack, store.value.getAbstractCreature(result._2))

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


  def melee (attaque : Array[Int], target: AbstractCreature): Boolean=
  {
    val numberOfAttacks = attaque(0)
    val precision = attaque(1)
    val damage = attaque(2)
    val numberOfDices = attaque (3)
    val sizeOfDice = attaque(4)

    var numberOfAttackLeft = numberOfAttacks

    while(!target.isDead && numberOfAttackLeft>0){
      val dicePourToucher = dice(1,20)
      val toucher = precision + numberOfAttackLeft*5 + dicePourToucher

      val targetName = target.name

      numberOfAttackLeft -= 1

      if (toucher > target.armor ){
        var degats = damage +  dice(numberOfDices, sizeOfDice)

        // La il y a un truc en plus mais je sais pas pourquoi, j'ai pas trouvé cette règle

        var degatsEffectifs = math.max(0,(degats-target.damageReduction))
        target.hp -= degatsEffectifs

        println(s"$name inflige $degatsEffectifs a $targetName")
      }
      else
        println (s"$name rate $targetName")
    }
    true
  }

}
