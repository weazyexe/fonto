package dev.weazyexe.fonto

expect val platform: String

class Greeting {
    fun greeting() = "Hello, $platform!"
}