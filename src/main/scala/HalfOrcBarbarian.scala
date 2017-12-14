/**
  * Created by Etudes on 13/12/2017.
  */
class HalfOrcBarbarian extends AbstractCreature("Barbarian Half Orc") {

  var hp = 142
  override val hpMax: Int = 142
  var armor = 17


  var baseMeleeAttack = Array(3, 4, 10, 1, 8, 19, 0)//???

  val team = "Enemies"

}
