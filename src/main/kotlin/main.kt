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

    fun printJust(x: Maybe) {
        // pattern matching
        when (x) {
            is Maybe.Just -> println("${x.value}")
            is Maybe.None -> println("None")
        }
    }

    val num = Maybe.Just(5.0)
    printJust(num) // 5.0

    //// It's time to make some lists... ////

    /* "Modular design brings with it great productivity improvements.
   First of all, small modules can be coded quickly and easily. Second,
   general-purpose modules can be reused, leading to faster development
   of subsequent programs. Third, the modules of a program can be tested
   independently, helping to reduce the time spent debugging."
   John Hughes, "Why Functional Programming Matters", 1990
    */

    // []
    val a = MyList.Nil
    // [1]
    val b = MyList.Cons(1, MyList.Nil)
    // [1, 2]
    val c = MyList.Cons(1, MyList.Cons(2, MyList.Nil))

    fun sum(list: MyList<Int>): Int {
        val res = when (list) {
            is MyList.Nil  -> 0
            is MyList.Cons -> list.head + sum(list.tail)
        }
        return res
    }

    println("sum([1,2]) = ${sum(c)}") // 3

    /* Only `0` and `+` in the definition of `sum` are specific to computing a sum.
    This means that the computation of a sum can be modularized by gluing together
    a general recursive pattern with `0` and `+`. This recursive pattern is
    conventionally called `foldr` (right fold).
     */
    fun <A, B> foldr(plus: (A, B) -> B, zero: B, list: MyList<A>): B {
        val res = when (list) {
            is MyList.Nil  -> zero
            is MyList.Cons -> plus(list.head, foldr(plus, zero, list.tail))
        }
        return res
    }

    fun newSum(xs: MyList<Int>) = foldr(Int::plus,0, xs)
    println("newSum([1,2]) = ${newSum(c)}") // 3

    /* And now `foldr` can be reused! */

    // note zero = 1 here
    fun product(xs: MyList<Int>) = foldr(Int::times,1, xs)
    fun anyTrue(xs: MyList<Boolean>) = foldr( Boolean::xor, false, xs)
    fun allTrue(xs: MyList<Boolean>) = foldr( Boolean::and, true , xs)

    // [4, 1, 2]
    val ints = MyList.Cons(4, c)
    println("product([4,1,2]) = ${product(ints)}") // 8
    // [false, true]
    val bools = MyList.Cons(false, MyList.Cons(true, MyList.Nil))
    println("anyTrue([0,1]) = ${anyTrue(bools)}") // true
    println("allTrue([0,1]) = ${allTrue(bools)}") // false

    /* "One way to understand `foldr` is as a function that replaces all occurrences
    of `Cons` in a list by `plus`, and all occurrences of `Nil` by `zero`."
    Cons(1, Cons(2, Nil))
    plus(1, plus(2,   0))
     */

    // Thus foldr(Cons, Nil, xs) = xs, and we can reuse `foldr` even further,
    // e.g. we can substitute `Nil` with another list:
    fun <T> append(xs: MyList<T>, ys: MyList<T>): MyList<T> = foldr(::MyList.Cons<T>, ys, xs)
}