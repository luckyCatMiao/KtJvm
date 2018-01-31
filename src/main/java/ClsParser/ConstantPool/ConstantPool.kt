package ClsParser.ConstantPool

import ClsParser.Attritubes.Attritube

class ConstantPool(
        private val innerMap: MutableMap<Int,Constant> = HashMap<Int,Constant>()
): MutableMap<Int,Constant>by innerMap{


    override fun toString(): String {

        return "ConstantPool(constants=$innerMap)"
    }


}