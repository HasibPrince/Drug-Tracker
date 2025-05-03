package com.hasib.startup.ui

import android.app.Application

object ResourceProvider {
    private lateinit var context: Application

    fun setup(context: Application) {
        this.context = context
    }

    fun getString(resId: Int): String {
        return context.getString(resId)
    }
}
