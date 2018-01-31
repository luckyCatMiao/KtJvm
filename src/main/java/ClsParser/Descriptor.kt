package ClsParser

import ClsParser.ConstantPool.Utf8Constant

/**
 * the base class for all number of class which have descriptors
 */
abstract class Descriptor {

    val reg="""(B|S|C|I|J|F|D|Z)""".toRegex()

     fun parseDescriptor(des: Utf8Constant){
         val v=des.value
         val match = reg.matchEntire(v)


         println(match?.value)
     }
}