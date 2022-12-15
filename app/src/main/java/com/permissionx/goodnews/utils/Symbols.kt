package com.permissionx.goodnews.utils

fun Int.addSymbols(): String = if (this > 0.0 && this != 0) "+$this" else "$this"