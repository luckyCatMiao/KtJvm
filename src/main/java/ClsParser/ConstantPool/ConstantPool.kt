package ClsParser.ConstantPool

class ConstantPool(){
    val map=HashMap<Int,Constant>()
    /**
     * 有的常量占两格，所所以可能会空出索引,所以还是用map好了
     */
    fun add(c: Constant, index: Int) {
        map.put(index,c)
    }

    override fun toString(): String {
        return "ConstantPool(constants=$map)"
    }


}