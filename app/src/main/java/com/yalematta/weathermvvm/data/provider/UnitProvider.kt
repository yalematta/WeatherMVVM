package com.yalematta.weathermvvm.data.provider

import com.yalematta.weathermvvm.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem() : UnitSystem
}