package com.north.wheremap.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}