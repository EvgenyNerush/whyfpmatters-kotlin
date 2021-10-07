import kotlin.math.PI
import kotlin.math.sin

fun main() {
    //// Functions are the first-class passengers ////

    // any function of Pi
    fun ofPi(f: (Double) -> Double): Double {
        return f(PI)
    }

    val r = ofPi(::sin) // note `::`
    println("$r") // 1.2e-16

    // Composition of functions, f.g: (f.g)(x) = f(g(x))
    // note a closure (lambda function)
    fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C =
        { x -> f(g(x)) }

    // `n` times apply `f`;
    // Recursion instead of a loop;
    // Without `x`, just functions and composition
    fun apply(f: (Double) -> Double, n: Int): (Double) -> Double =
        if (n == 1) f else compose(apply(f, n - 1), f)

    // let's try
    fun g(x: Double) = x * x
    val h = apply(::g, 2) // (x^2)^2 = x^4
    println("${h(2.0)}") // 16.0

    //// Algebraic data types: see classes.kt ////

    fun printJust(x: Just) {
        when (x) {
            is Just.Some -> println("${x.value}")
            is Just.None -> println("None")
        }
    }
    val num = Just.Some(5.0)
    printJust(num) // 5.0

    //// It's time to make some lists... ////
}