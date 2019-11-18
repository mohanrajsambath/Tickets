package com.ganesh.tickets.view.adapter

interface BindableAdapter<T> {
    fun setData(items: List<T>)
}