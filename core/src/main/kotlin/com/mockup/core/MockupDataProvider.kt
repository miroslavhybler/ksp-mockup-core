@file:Suppress("RedundantVisibilityModifier")

package com.mockup.core


import kotlin.reflect.KClass

/**
 * Defines the mockup data provider class.
 * For more information visit [Github repository](https://github.com/miroslavhybler/ksp-mockup)
 * Report issue [here](https://github.com/miroslavhybler/ksp-mockup/issues)
 * @param values Generated mockup data, must be not empty
 *
 * Compose Preview support is optional. Generated provider classes can implement
 * `PreviewParameterProvider` when KSP input option `mockup.usePreviewParameterProviders`
 * is enabled, while this core type stays free of UI dependencies.
 * @author Miroslav Hýbler <br>
 * created on 19.12.2025
 * @since 2.0.O
 */
public abstract class MockupDataProvider<T : Any> constructor(
    values: Sequence<T>,
    val clazz: KClass<T>,
) {

    public open val values: Sequence<T> = run {
        val customValues = Mockup.getCustomValues(clazz = clazz)
        if (customValues.isNullOrEmpty()) {
            values
        } else {
            customValues.asSequence() + values
        }
    }

    /**
     * Returns the first element from the [values] [Sequence].
     * @since 2.0.O
     */
    @Deprecated(message = "use first()")
    val single: T get() = values.first()


    /**
     * Returns the first element from the [values] [Sequence].
     * @since 2.0.O
     */
    val first: T get() = values.first()


    /**
     * Returns [values] as [List].
     * @since 2.0.O
     */
    val list: List<T> get() = values.toList()


    /**
     * Returns a random element from the [values] [Sequence].
     * @since 2.0.O
     */
    val random: T get() = list.random()


    /**
     * Returns the number of elements in the [values] [Sequence].
     * @since 2.0.O
     */
    public open val count: Int
        get() = values.count()

}
