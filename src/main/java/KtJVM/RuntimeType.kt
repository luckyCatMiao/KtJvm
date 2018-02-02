package KtJVM

import kotlin.reflect.KClass

enum class MemberType {
    Class, Field, Method
}

open class TypeInfo()
data class FieldTypeInfo(val type: RuntimeType<out Any>) : TypeInfo()
class MethodTypeInfo() : TypeInfo()
class ClassTypeInfo() : TypeInfo()


class RuntimeType<T:Any>(private val k: KClass<T>):KClass<T> by k{

}