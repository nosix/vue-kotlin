@file:Suppress("unused", "UnsafeCastFromDynamic", "NOTHING_TO_INLINE")

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
 * Type of `void 0`
 */
external interface Void

/**
 * Constant of `void 0`
 */
val void: Void = js("void 0")

/**
 * `{ [propertyName: String]: T }`
 */
external interface JsonOf<T>

inline operator fun <T> JsonOf<T>.get(propertyName: String): T = this.asDynamic()[propertyName]
inline operator fun <T> JsonOf<T>.set(propertyName: String, value: T) {
    this.asDynamic()[propertyName] = value
}

/**
 * `T | Array<T>`
 */
external interface OneOrMany<T>

inline fun <T> OneOrMany(value: T): OneOrMany<T> = value.asDynamic()
inline fun <T> OneOrMany(value: Array<T>): OneOrMany<T> = value.asDynamic()
