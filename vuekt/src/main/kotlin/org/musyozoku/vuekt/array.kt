@file:Suppress("unused", "UnsafeCastFromDynamic", "NOTHING_TO_INLINE")

// See also: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array

package org.musyozoku.vuekt

/**
 * The push() method adds one or more elements to the end of an array and returns the new length of the array.
 */
inline fun <T> Array<T>.push(vararg element: T): Int
        = this.asDynamic().push.apply(this, element)

/**
 * The pop() method removes the last element from an array and returns that element.
 * This method changes the length of the array.
 */
inline fun <T> Array<T>.pop(): T?
        = this.asDynamic().pop()

/**
 * The shift() method removes the first element from an array and returns that element.
 * This method changes the length of the array.
 */
inline fun <T> Array<T>.shift(): T?
        = this.asDynamic().shift()

/**
 * The unshift() method adds one or more elements to the beginning of an array and returns the new length of the array.
 */
inline fun <T> Array<T>.unshift(vararg element: T): Int
        = this.asDynamic().unshift.apply(this, element)

/**
 * The splice() method changes the contents of an array by removing existing elements.
 */
inline fun <T> Array<T>.splice(index: Int): Array<T>
        = this.asDynamic().splice.apply(this, arrayOf(index))

/**
 * The splice() method changes the contents of an array by removing existing elements.
 */
inline fun <T> Array<T>.splice(index: Int, howMany: Int): Array<T>
        = this.asDynamic().splice.apply(this, arrayOf(index, howMany))

/**
 * The splice() method changes the contents of an array by removing existing elements and adding new elements.
 */
inline fun <T> Array<T>.splice(index: Int, howMany: Int, vararg element: T): Array<T>
        = this.asDynamic().splice.apply(this, arrayOf(index, howMany) + element)

/**
 * The sort() method sorts the elements of an array in place and returns the array.
 * The sort is not necessarily stable.
 * The default sort order is according to string Unicode code points.
 */
inline fun <T> Array<T>.sort(): Array<T>
        = this.asDynamic().sort()

/**
 * The sort() method sorts the elements of an array in place and returns the array.
 * The sort is not necessarily stable.
 */
inline fun <T> Array<T>.sort(noinline compareFunction: (a: T, b: T) -> Int): Array<T>
        = this.asDynamic().sort(compareFunction)

/**
 * The reverse() method reverses an array in place.
 * The first array element becomes the last, and the last array element becomes the first.
 */
inline fun <T> Array<T>.reverse(): Array<T>
        = this.asDynamic().reverse()
