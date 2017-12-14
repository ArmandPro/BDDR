/**
  * Created by Etudes on 13/12/2017.
  */
class HalfOrcBarbarian extends AbstractCreature("Barbarian Half Orc",1) {

  var armor = 17

  override var hpMAx: Int = 142

  var hp = 142

  var baseMeleeAttack = Array(3, 4, 10, 1, 8, 19, 0)//???

  val team = "Enemy"

}
