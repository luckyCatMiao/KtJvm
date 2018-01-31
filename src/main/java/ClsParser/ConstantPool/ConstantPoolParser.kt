package ClsParser.ConstantPool

import KtEx.valueOfObject
import ClsParser.ClassChecker
import ClsParser.JavaClass
import Tool.DataReader
import Tool.ReflectionUtil
import Tool.equal
import com.google.common.base.Charsets
import com.google.common.base.Preconditions
import io.reactivex.Observable
import kotlin.reflect.KProperty


class ConstantPoolParser(val javaclass: JavaClass, val reader: DataReader) {
    private val pool = ConstantPool()

    fun parse(): ConstantPool {

        val count = reader.readUnsignedShort()
        /**
         * if constant pool contains long and double,it will consume two slot(我TM也不知道为什么规范要这么设计)
         */
        var i=1
        while(i<count)
        {
            val slotConsume=parseConstant(i)
            i+=slotConsume
        }
        ClassChecker.checkIndexConstantPool(pool)
        return pool

    }

    /**
     * read from stream,then try to parse one constant
     */
    private fun parseConstant(i: Int):Int {
        val tag = reader.readUnsignedByte()
        //use reflection to call function
        val klass = ConstantType::class.java.kotlin
        Observable.fromIterable(klass.members)
                .filter { ReflectionUtil.isProperty(it) }
                .cast(KProperty::class.java)
                .filter { equal(it.valueOfObject(ConstantType), tag) }
                .map { toParseMethodName(it) }
                .toList()
                .subscribe ({
                    Preconditions.checkState(it.size == 1, "internal error!method  parseTag:${tag} do not exist!")
                    //call functions by names
                    ReflectionUtil.callMethodByName(it[0], this, { pool.add(it as Constant,i) })
                },{
                     throw RuntimeException("error")
                })


        /**
         * if constant pool contains long and double,it will consume two slot(我TM也不知道为什么规范要这么设计)
         */
        return when(tag)
        {
            ConstantType.CONSTANT_Long,ConstantType.CONSTANT_Double->2
            else -> 1
        }

    }


    fun parseInteger(): IntegerConstant {
        val int = reader.readInt()
        return IntegerConstant(int)
    }

    fun parseFloat(): FloatConstant {
        val float = reader.readFloat()
        return FloatConstant(float)
    }

    fun parseLong(): LongConstant {
        val long = reader.readLong()
        return LongConstant(long)
    }

    fun parseDouble(): DoubleConstant {
        val double = reader.readDouble()
        return DoubleConstant(double)
    }

    fun parseUtf8(): Utf8Constant {
        val length = reader.readUnsignedShort()
        val bytes = reader.readFully(length)
        val str = String(bytes, Charsets.UTF_8)
        return Utf8Constant(str)
    }

    fun parseString(): StringConstant {
        val string_index = reader.readUnsignedShort()
        return StringConstant(string_index)
    }


    fun parseClass(): ClassConstant {
        val name_index = reader.readUnsignedShort()
        return ClassConstant(name_index)
    }

    fun parseNameAndType(): NameAndTypeConstant {
        val i1 = reader.readUnsignedShort()
        val i2 = reader.readUnsignedShort()
        return NameAndTypeConstant(i1, i2)
    }

    fun parseFieldref(): FieldrefConstant {
        val i1 = reader.readUnsignedShort()
        val i2 = reader.readUnsignedShort()
        return FieldrefConstant(i1, i2)
    }

    fun parseMethodref(): MethodrefConstant {
        val i1 = reader.readUnsignedShort()
        val i2 = reader.readUnsignedShort()
        return MethodrefConstant(i1, i2)
    }

    fun parseInterfaceMethodref(): InterfaceMethodrefConstant {
        val i1 = reader.readUnsignedShort()
        val i2 = reader.readUnsignedShort()
        return InterfaceMethodrefConstant(i1, i2)
    }


    private fun toParseMethodName(it: KProperty<*>) = "parse" + it.name.replace("CONSTANT_", "")


}