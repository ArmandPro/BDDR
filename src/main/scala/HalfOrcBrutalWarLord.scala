/**
  * Created by Etudes on 13/12/2017.
  */
class HalfOrcBrutalWarLord extends AbstractCreature("Brutal Warlord Half Orc") {

  var hp = 141
  override val hpMax: Int = 141
  var armor = 27


  var baseMeleeAttack = Array(3, 4, 10, 1, 8, 19, 0)//???

  val team = "enemy"

}
