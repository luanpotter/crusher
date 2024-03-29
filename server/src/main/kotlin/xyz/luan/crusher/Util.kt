package xyz.luan.crusher

import java.io.InputStream

fun getResourceAsStream(path: String): InputStream? {
    return object {}.javaClass.getResource(path).openStream() ?: throw RuntimeException("File not found: $path")
}

fun nullOrEmpty(value: String?): Boolean = value == null || value.isEmpty()