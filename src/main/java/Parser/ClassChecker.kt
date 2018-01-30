package Parser

import Parser.ConstantPool.ConstantPool
import com.sun.org.apache.xpath.internal.operations.Bool

/**
 * check class structure
 */
object ClassChecker{

    fun checkMagic(magic: Long)=magic==0xCAFEBABE

    fun checkVersion(minorVersion: Int, majorVersion: Int): Boolean {
        //only support class whose majorVersion was between 45 and 52
        //minorVersion will be ignored currently

        return majorVersion in 45..52

    }



    fun checkFlag(flag: Int): Boolean {

        return true
    }


    /**
     * check index of each constant
     */
    fun checkIndexConstantPool(pool: ConstantPool): Unit {
        pool.list.forEach{
            //
        }

    }

    /**
     * check single index
     */
    fun checkIndexConstantPoolSingle(pool: ConstantPool,index:Int): Boolean {

        return true
    }


    /**
     * return a description of error
     */
    fun parseError(name: String) = "parse contant pool error! reason:${name}"

}