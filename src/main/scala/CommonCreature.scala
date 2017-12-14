/**
  * Created by Etudes on 14/12/2017.
  */

import scala.collection.mutable.ArrayBuffer

object CommonCreature extends Serializable {

   var storage: ArrayBuffer[AbstractCreature] = ArrayBuffer.empty[AbstractCreature]


  def addAbstractCreature(creature: AbstractCreature): Int = {
    storage += creature

    return storage.length - 1
  }

  def getAbstractCreature(id: Int): AbstractCreature = {
    return storage(id)
  }





}
