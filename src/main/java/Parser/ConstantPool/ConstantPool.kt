package Parser.ConstantPool

class ConstantPool(){
    val list=ArrayList<Constant>()
    fun add(c: Constant) {
        list.add(c)
    }

    override fun toString(): String {
        return "ConstantPool(constants=$list)"
    }


}