package ClsParser.Attritubes

import ClsParser.ConstantPool.NumberConstant
import KtJVM.Code.Code

open class Attritube(){

}




class ConstantValueAttritube(value: NumberConstant) :Attritube()
class CodeAttribute(codes: List<Code>) :Attritube()