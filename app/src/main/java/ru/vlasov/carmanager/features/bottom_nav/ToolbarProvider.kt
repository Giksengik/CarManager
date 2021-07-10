package ru.vlasov.carmanager.features.bottom_nav

import androidx.appcompat.widget.Toolbar

interface ToolbarProvider {
    fun getToolbar() : Toolbar
    fun setUpNavigationDrawer()
}