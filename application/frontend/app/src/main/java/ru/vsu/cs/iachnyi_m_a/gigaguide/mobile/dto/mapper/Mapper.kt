package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper

interface Mapper <F,S> {
    fun map(value: F): S
}