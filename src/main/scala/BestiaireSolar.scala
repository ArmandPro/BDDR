/**
  * Created by Etudes on 13/12/2017.
  */
 class BestiaireSolar() extends AbstractCreature("Solar") {

  var hp = 363
  override val hpMax: Int = 363
  var armor = 44
  override val regen = 15
  override val damageReduction: Int = 0

  var baseMeleeAttack = Array(4, 15, 18, 3, 6, 21, 0)

  val team = "Allies"

}
