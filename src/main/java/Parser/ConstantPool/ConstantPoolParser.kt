package Parser.ConstantPool

import KtEx.valueOfObject
import Parser.DataReader
import Parser.JavaClass
import com.google.common.base.Charsets
import com.google.common.base.Preconditions.checkState
import io.reactivex.Observable
import kotlin.reflect.KFunction
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
        val klass=ConstantTag::class.java.kotlin
        Observable.fromIterable(klass.members)
                .filter { Helper.isProperty(it)}
                .cast(KProperty::class.java)
                .filter { Helper.equal(it.valueOfObject(ConstantTag),tag) }
                .map { Helper.toMethodName(it) }
                .subscribe{
                    //call functions by names
                    Helper.callMethodByName(it,this,pool)
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

    /**
     * internal helper object
     */
    object Helper
    {
        fun isProperty(it:Any)=it is KProperty<*>
        fun isFunction(it:Any)=it is KFunction<*>
        fun equal(a:Any?,b:Any?):Boolean{
             if(a==null||b==null)
            {
                return false
            }else{
                return a.equals(b)
            }

        }

        fun toMethodName(it: KProperty<*>)="parse"+it.name.replace("CONSTANT_","")


        /**
         * use reflection to call parsing method then add resulted constant to pool
         */
        fun callMethodByName(name: String, target: Any, pool: ConstantPool) {
            val klass=target::class.java.kotlin
            Observable.fromIterable(klass.members)
                    .filter { isFunction(it)}
                    .cast(KFunction::class.java)
                    .filter { equal(it.name,name) }
                    .toList()
                    .subscribe({
                        println(it.size)
                        checkState(it.size==1,"internal error!method ${name} do not exist!")
                        val c=it[0].call(target) as Constant
                        pool.add(c)
                    },{
                        RuntimeException("rxjava internal error")
                    })
        }
    }

}