package Parser

class JavaClass()
{
    var magic: Long=0
    var minorVersion: Int=0
    var majorVersion: Int=0

    override fun toString(): String {
        return "JavaClass(magic=$magic, minorVersion=$minorVersion, majorVersion=$majorVersion)"
    }


}