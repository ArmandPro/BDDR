/**
  * Created by Etudes on 13/12/2017.
  */
 class BestiaireSolar() extends AbstractCreature("Solar",1) {

  var hp = 363

  override var hpMAx: Int = 363

  var armor = 44

  override val reg = 15

  override val damageRed: Int = 0

  var baseMeleeAttack = Array(4, 15, 18, 3, 6, 21, 0)

  val team = "A" //side ally : DELE

}
