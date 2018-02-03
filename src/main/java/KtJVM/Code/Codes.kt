package KtJVM.Code

import ClsParser.Attritubes.AttritubesManager


class CodeManager(val innerList: ArrayList<Code> =ArrayList<Code>()):MutableList<Code> by innerList{
    /**
     * attrs
     */
    val attrMan: AttritubesManager = AttritubesManager()

}



open class Code(val byteLength: Byte) {

}


/**
 * load variable from local varitable to operStack
 */
data class Code_Load(val index: Int) :Code(1)
data class Code_Const(val const: Int) :Code(1)
data class Code_Store(val index: Int) :Code(1)
data class Code_InvokeSpecial(val methodIndex:Int):Code(3)
class Code_Return():Code(1)