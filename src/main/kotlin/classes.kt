// just an example of a class
class MyPair(i: Int, j: Int) { /*...*/ }

// `object` keyword creates a type with only one instance, i.e. a singleton
object MyUselessSingleton { /* something can be here */ }

// Algebraic data types: an example;
// `sealed` ~ not extendable
sealed class Maybe {
    // singleton `None` inheriting `Maybe`
    object None : Maybe()
    // data class holds data only
    data class Just(val value: Double) : Maybe()
}

// list as a recursive class: it is either an empty list
// or a value (head) attached to another list;
// Note `out` and `Nothing` (type with no instance) here
sealed class MyList<out T> {
    object Nil : MyList<Nothing>() // object can't have a generic type in Kotlin
    data class Cons<T>(val head: T, val tail: MyList<T>) : MyList<T>()
}