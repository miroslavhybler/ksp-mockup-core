package com.mockup.core

import kotlin.reflect.KClass

/**
 * Provides custom mockup values that are prepended before generated values.
 */
public interface CustomMockupProvider<T : Any> {
    val clazz: KClass<T>
    val values: List<T>
}
