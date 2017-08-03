package com.islam.amaan.tracknamaaz.extentions

import kotlin.reflect.KProperty

/**
 * Created by Amaan on 23-07-2017.
 */


object DelegatesExt{
    fun <T> notNullSingleValue()= NotNullSingleValue<T>()
}

class NotNullSingleValue<T> {
private var value:T? = null
    operator fun getValue(thisRef:Any?,property:KProperty<*>):T{
        return value?:throw IllegalStateException("${property.name} is not initialized")
    }
    operator fun setValue(thisRef: Any?,property: KProperty<*>,value: T){
        this.value=if(this.value!=null)value
        else throw IllegalStateException("${property.name} is already initialized")
    }
}
