@file:Suppress("NOTHING_TO_INLINE", "UnsafeCastFromDynamic", "unused")

package org.musyozoku.vuekt

/**
 * JavaScript native `this`
 */
external val `this`: dynamic

/**
 * Typed JavaScript native `this`
 */
inline fun <T : Any> thisAs(): T = `this`

/**
 * `{ [propertyName: String]: T }`
 */
external interface TypedMap<T>

inline operator fun <T> TypedMap<T>.get(propertyName: String): T = this.asDynamic()[propertyName]
inline operator fun <T> TypedMap<T>.set(propertyName: String, value: T) {
    this.asDynamic()[propertyName] = value
}
