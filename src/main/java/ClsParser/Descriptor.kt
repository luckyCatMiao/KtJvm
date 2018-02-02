package ClsParser

import ClsParser.Attritubes.AttritubesManager
import ClsParser.ConstantPool.Utf8Constant
import com.google.common.base.Preconditions
import io.reactivex.Observable
import kotlin.reflect.KClass

enum class MemberType{
    Class,Field,Method
}

open class TypeInfo()
class FieldTypeInfo(type: RuntimeType) : TypeInfo()
class MethodTypeInfo(): TypeInfo()
class ClassTypeInfo(): TypeInfo()

/**
 * the base class for all number of class which have descriptors
 */
abstract class Descriptor(val memberType:MemberType) {


    private val runtimeType= listOf(
        Byte::class,
        Short::class,
        Char::class,
        Int::class,
        Long::class,
        Float::class,
        Double::class,
        Boolean::class
    )


    /**
     * attrs
     */
    val attrMan: AttritubesManager = AttritubesManager()

    lateinit var typeInfo:TypeInfo
        private set


    val f_reg1 = """[BSCIJFDZ]""".toRegex()
    val f_reg2 = """L.+?;""".toRegex()
    val f_reg3 = """\[+[BSCIJFDZ]""".toRegex()
    val f_reg4 = """\[+L.+?;""".toRegex()

     fun parseFieldDescriptor(des: Utf8Constant){
         val v=des.value

         var match = f_reg1.matchEntire(v)
         if(match!=null)
         {
             parseFieldType(match.value)
             return
         }

          match = f_reg2.matchEntire(v)
         if(match!=null)
         {
             return
         }

         match = f_reg3.matchEntire(v)
         if(match!=null)
         {
             return
         }

         match = f_reg4.matchEntire(v)
         if(match!=null)
         {
             return
         }



     }

    private fun parseFieldType(value: String) {
        fun getMemberByName(value: String): KClass<out Any> {
//            var t:KClass<out Any>
//
//            Observable.fromIterable(runtimeType)
//                    .filter { it.simpleName!!.toUpperCase().startsWith(value)}
//                    .toList()
//                    .subscribe( {
//                        Preconditions.checkState(it.size==0,"parse field type Error!")
//                        t=it[0]
//                    },{
//                        throw RuntimeException("parse field type Error!")
//                    })

            return runtimeType.filter {  it.simpleName!!.toUpperCase().startsWith(value)}[0]
        }

        //基本类型 byte 、 short 、 char 、 int 、 long 、 float 和 double 的描述符是单个字母，分别对应 B 、 S 、 C 、 I 、
        //J 、 F 和 D 。注意， long 的描述符是 J 而不是 L 。

        val type=when(value){
            "J"->Long::class
            "Z"->Boolean::class
            "B","S","C","I","F","D"->getMemberByName(value)
            else -> throw RuntimeException("fieldType parse error! the value:$value can't be parsed!")
        }



        this.typeInfo=FieldTypeInfo(type)

    }
}