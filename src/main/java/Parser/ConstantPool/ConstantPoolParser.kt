package Parser.ConstantPool

import KtEx.times
import KtEx.valueOfObject
import Parser.DataReader
import Parser.JavaClass
import Tool.ReflectionUtil
import Tool.equal
import com.google.common.base.Charsets
import io.reactivex.Observable
import kotlin.reflect.KProperty


class ConstantPoolParser(val javaclass:JavaClass,val reader:DataReader) {
    private val pool=ConstantPool()

    fun parse() {

        var count=reader.readUnsignedShort()
        //according to the specification,only (count-1) constants exist in the constants pool
        count-=1
//        count.times {
//            parseConstant()
//        }
        parseConstant()
        println(pool)
    }

    /**
     * read from stream,then try to parse one constant
     */
    private fun parseConstant() {
        val tag=reader.readUnsignedByte()
        //use reflection to call function
        val klass= ConstantType::class.java.kotlin
        Observable.fromIterable(klass.members)
                .filter { ReflectionUtil.isProperty(it)}
                .cast(KProperty::class.java)
                .filter { equal(it.valueOfObject(ConstantType),tag) }
                .map { toParseMethodName(it) }
                .subscribe{
                    //call functions by names
                    ReflectionUtil.callMethodByName(it,this,{ pool.add(it as Constant)})
                }


    }



    fun parseInteger(): IntegerConstant {
        val int = reader.readInt()
        return IntegerConstant(int)
    }

    fun parseFloat(): FloatConstant {
        val float=reader.readFloat()
        return FloatConstant(float)
    }

    fun parseLong(): LongConstant {
        val long=reader.readLong()
        return LongConstant(long)
    }

    fun parseDouble():DoubleConstant{
        val double=reader.readDouble()
        return DoubleConstant(double)
    }

    fun parseUtf8():UTF8Constant{
        val length=reader.readUnsignedShort()
        val bytes=reader.readFully(length)
        val str=String(bytes,Charsets.UTF_8)
        return UTF8Constant(str)
    }

    fun parseString():StringConstant{
        val string_index=reader.readUnsignedShort()
        return StringConstant(string_index)
    }

    fun parseClass():ClassConstant{
        val name_index=reader.readUnsignedShort()
        return ClassConstant(name_index)
    }

    fun parseNameAndType(): NameAndTypeConstant {
        val i1=reader.readUnsignedShort()
        val i2=reader.readUnsignedShort()
        return NameAndTypeConstant(i1,i2)
    }

    fun parseFieldref(): FieldrefConstant {
        val i1=reader.readUnsignedShort()
        val i2=reader.readUnsignedShort()
        return FieldrefConstant(i1,i2)
    }

    fun parseMethodref(): MethodrefConstant {
        val i1=reader.readUnsignedShort()
        val i2=reader.readUnsignedShort()
        return MethodrefConstant(i1,i2)
    }

    fun parseInterfaceMethodref(): InterfaceMethodrefConstant {
        val i1=reader.readUnsignedShort()
        val i2=reader.readUnsignedShort()
        return InterfaceMethodrefConstant(i1,i2)
    }

    private fun toParseMethodName(it: KProperty<*>)="parse"+it.name.replace("CONSTANT_","")


}