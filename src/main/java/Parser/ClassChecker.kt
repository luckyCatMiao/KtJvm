package Parser

object ClassChecker{

    fun checkMagic(magic: Long)=magic==0xCAFEBABE

    fun checkVersion(minorVersion: Int, majorVersion: Int): Boolean {
        //only support class whose majorVersion was between 45 and 52
        //minorVersion will be ignored currently

        return majorVersion in 45..52

    }

}