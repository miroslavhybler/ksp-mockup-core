@file:Suppress("RedundantVisibilityModifier")

package com.mockup.core

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.jvm.Throws
import kotlin.reflect.full.primaryConstructor


/**
 * Public object to access generated mockup providers by extensions from generated
 * `com.mockup.providers` package.
 * @author Miroslav Hýbler <br>
 * created on 19.12.2025
 * @since 2.0.0
 */
public object Mockup {

    /**
     * Returns the first element from the generated sequence of the mockup data for the given type [T].
     *
     * @return The first element from the sequence.
     * @throws IllegalArgumentException if the provider for the given type is not found.
     * @since 2.0.0
     */
    public inline fun <reified T : Any> get(): T = getProvider(clazz = T::class.java).first

    /**
     * Returns the first element from the generated sequence of the mockup data for the given type [T], or `null` if the provider is not found.
     *
     * @return The first element from the sequence, or `null`.
     * @since 2.0.0
     */
    public inline fun <reified T : Any> getOrNull(): T? =
        getProviderOrNull(clazz = T::class.java)?.first

    /**
     * Returns a random element from the generated sequence of the mockup data for the given type [T].
     *
     * @return A random element from the sequence.
     * @throws IllegalArgumentException if the provider for the given type is not found.
     * @since 2.0.0
     */
    public inline fun <reified T : Any> getRandom(): T =
        getProvider(clazz = T::class.java).random

    /**
     * Returns a random element from the generated sequence of the mockup data for the given type [T], or `null` if the provider is not found.
     *
     * @return A random element from the sequence, or `null`.
     * @since 2.0.0
     */
    public inline fun <reified T : Any> getRandomOrNull(): T? =
        getProviderOrNull(clazz = T::class.java)?.random


    /**
     * Returns the whole sequence of the generated mockup data for the given type [T] as a [List].
     *
     * @return The list of generated mockup data.
     * @throws IllegalArgumentException if the provider for the given type is not found.
     * @since 2.0.0
     */
    public inline fun <reified T : Any> getList(): List<T> =
        getProvider(clazz = T::class.java).list


    /**
     * Returns the whole sequence of the generated mockup data for the given type [T] as a [List], or `null` if the provider is not found.
     *
     * @return The list of generated mockup data, or `null`.
     * @since 2.0.0
     */
    public inline fun <reified T : Any> getListOrNull(): List<T>? =
        getProviderOrNull(clazz = T::class.java)?.list


    /**
     * @param json Json string to be parsed.
     * @return Parsed object of type [T].
     * @throws SerializationException in case of any decoding-specific error
     * @throws IllegalArgumentException if the decoded input is not a valid instance of [T]
     * @since 2.0.0
     */
    @Throws(
        exceptionClasses = [
            SerializationException::class,
            IllegalArgumentException::class
        ]
    )
    public inline fun <reified T : Any> fromJson(
        json: String
    ): T {
        return Json.decodeFromString<T>(string = json)
    }


    /**
     * @param json Json string to be parsed.
     * @return Parsed object of type [T] or null when [json] cannot be parsed into the type.
     * @since 2.0.0
     */
    public inline fun <reified T : Any> fromJsonOrNull(
        json: String
    ): T? {
        return try {
            fromJson<T>(json = json)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    /**
     * Returns the generated [MockupDataProvider] for the given type [T].
     *
     * This function uses reflection to find the generated provider class.
     *
     * @return An instance of the generated [MockupDataProvider].
     * @throws IllegalArgumentException if the provider for the given type is not found.
     * This can happen if the class is not annotated with `@Mockup` or the project has not been rebuilt.
     * @since 2.0.0
     */
    public fun <T : Any> getProvider(clazz: Class<T>): MockupDataProvider<T> {
        val className = "${clazz.name}MockupProvider"
        try {
            val providerClass = Class.forName(className).kotlin
            val providerInstance = providerClass.primaryConstructor?.call()
                ?: throw IllegalArgumentException(
                    "MockupDataProvider for ${clazz.canonicalName} has no primary constructor. If this happen, create issue here https://github.com/miroslavhybler/ksp-mockup-processor/issues"
                )

            @Suppress("UNCHECKED_CAST")
            return providerInstance as MockupDataProvider<T>
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException(
                "MockupDataProvider for ${clazz.canonicalName} not found. " +
                        "Make sure the class is annotated with @Mockup and the project is built.",
                e
            )
        }
    }


    /**
     * Returns the generated [MockupDataProvider] for the given type [T].
     *
     * This function uses reflection to find the generated provider class.
     *
     * @return An instance of the generated [MockupDataProvider].
     * @throws IllegalArgumentException if the provider for the given type is not found.
     * This can happen if the class is not annotated with `@Mockup` or the project has not been rebuilt.
     * @since 2.0.0
     */
    public inline fun <reified T : Any> getProvider(): MockupDataProvider<T> =
        getProvider(clazz = T::class.java)


    /**
     * Returns the generated [MockupDataProvider] for the given type [T], or `null` if it's not found.
     *
     * This function is a safer alternative to [getProvider] and will not throw an exception if the provider is not found.
     *
     * @return An instance of the generated [MockupDataProvider] or `null`.
     * @since 2.0.0
     */
    public fun <T : Any> getProviderOrNull(clazz: Class<T>): MockupDataProvider<T>? {
        return try {
            getProvider(clazz)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }


    /**
     * Returns the generated [MockupDataProvider] for the given type [T], or `null` if it's not found.
     *
     * This function is a safer alternative to [getProvider] and will not throw an exception if the provider is not found.
     *
     * @return An instance of the generated [MockupDataProvider] or `null`.
     * @since 2.0.0
     */
    public inline fun <reified T : Any> getProviderOrNull(): MockupDataProvider<T>? =
        getProviderOrNull(clazz = T::class.java)


}