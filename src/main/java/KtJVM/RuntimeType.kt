package KtJVM

import kotlin.reflect.KClass

enum class MemberType {
    Class, Field, Method
}

open class TypeInfo()
class FieldTypeInfo(type: KClass<out Any>) : TypeInfo()
class MethodTypeInfo() : TypeInfo()
class ClassTypeInfo() : TypeInfo()