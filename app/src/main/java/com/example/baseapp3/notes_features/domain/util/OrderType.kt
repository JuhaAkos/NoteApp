package com.example.baseapp3.notes_features.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}