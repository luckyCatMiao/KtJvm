package ClsParser.Attritubes

import ClsParser.ConstantPool.NumberConstant
import KtJVM.Code.Code

open class Attritube(){

}


class NotSupportAttribute():Attritube()

class ConstantValueAttritube(value: NumberConstant) :Attritube()
class CodeAttribute(codes: List<Code>) :Attritube()
class LineNumberTableAttribute:Attritube()