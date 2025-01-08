package com.north.wheremap.core.domain.utils

import kotlinx.coroutines.Job
import java.util.concurrent.atomic.AtomicReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class AutoClosingJob<T> : ReadWriteProperty<Any, T?> where T : Job {

    private var currentValue = AtomicReference<T?>()

    override fun getValue(thisRef: Any, property: KProperty<*>): T? = currentValue.get()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        val oldValue = currentValue.getAndSet(value)
        oldValue?.cancel()
    }

}
