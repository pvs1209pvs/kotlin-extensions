@file:Suppress("NOTHING_TO_INLINE")

package strings

import com.cesarferreira.pluralize.pluralize
import com.cesarferreira.pluralize.singularize
import com.google.common.base.Strings
import me.carleslc.kotlinextensions.standard.letOrElse
import java.lang.IllegalArgumentException
import java.util.*

inline infix operator fun String.times(n: Int): String = Strings.repeat(this, n)
inline infix operator fun Int.times(s: String): String = Strings.repeat(s, this)

inline fun Double.toFixed(digits: Int): String = java.lang.String.format("%.${digits}f", this)
inline fun Float.toFixed(digits: Int): String = toDouble().toFixed(digits)

inline fun String?.isNotNullOrBlank() = !isNullOrBlank()

inline fun <T> T.toStringTransform(transform: (T) -> String) = let(transform)

inline fun <T> T?.toStringTransform(nullString: String = null.toString(), transform: (T) -> String) =
    letOrElse(nullString, transform)

inline fun String.pluralize() = pluralize()

inline fun String.pluralize(count: Int) = pluralize(count)

inline fun String.singularize() = singularize()

inline fun String.singularize(count: Int) = singularize(count)

inline fun String.splitLines() = split('\n')

inline fun <T> T?.wrap() = "$this"
inline fun <T> T?.wrapString() = wrap()

inline fun concat(vararg params: Any?) = params.joinToString("")

inline fun join(vararg params: Any?) = params.joinToString(" ")

inline fun joinWith(separator: String = " ", vararg params: Any?) = params.joinToString(separator)

inline fun String.replace(ignoreCase: Boolean = false, vararg vars: Pair<String, String>): String {
    var copy = this
    vars.forEach { copy = copy.replace(it.first, it.second, ignoreCase) }
    return copy
}

inline fun String.remove(substring: String) = replace(substring, "")

/**
 * Capitalized the first character of every word where word is delimited by a space character.
 */
fun String.capitalizeFirstChar(saveSpacing: Boolean = true): String {

    if (isEmpty() || isBlank()) throw IllegalArgumentException("Blank or empty String cannot be used.")

    val deliminator = if (saveSpacing) " " else " +"

    return this
        .lowercase(Locale.getDefault())
        .split(deliminator.toRegex())
        .joinToString(" ") {
            if (it.isEmpty()) it
            else it.replaceRange(0, 1, it.first().uppercaseChar().toString())
        }

}

/**
 * Removes non-numerical characters from a string.
 */
fun String.removeNonAlpha() =
    replace("[^a-zA-Z\\s]".toRegex(), " ").replace(" +".toRegex(), " ").trim()

/**
 * Removes duplicates words from a string. Words are separated by one of more space characters.
 * @return String with duplicate words removed
 */
fun String.uniquifyWords(): String {
    val multipleSpacesRegex = " +".toRegex()
    return this.split(multipleSpacesRegex).distinct().joinToString(" ")
}
