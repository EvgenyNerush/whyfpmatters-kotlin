// just an example of a class
class MyPair(i: Int, j: Int) { /*...*/ }

// `object` keyword creates a type with only one instance, i.e. a singleton
object MyUselessSingleton { /* something can be here */ }

// Algebraic data types: an example;
// `sealed` ~ not extendable
sealed class Just {
    // singleton `None` inherited (derived) from `Just`
    object None : Just()
    // data class holds data only
    data class Some(val value: Double) : Just()
}

// list as a recursive class
sealed class MyList<T> {
    object Nil : MyList<Nothing>() // object can't have a generic type in Kotlin
    data class Cons<T>(val head: T, val tail: MyList<T>) : MyList<T>()
}