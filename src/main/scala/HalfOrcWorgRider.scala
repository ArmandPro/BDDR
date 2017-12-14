/**
  * Created by Etudes on 14/12/2017.
  */
class HalfOrcWorgRider extends AbstractCreature("Worg Rider Half Orc") {

  var hp = 13
  override val hpMax: Int = 13
  var armor = 18


  var baseMeleeAttack = Array(3, 4, 10, 1, 8, 19, 0)//???

  val team = "Enemy"

}
