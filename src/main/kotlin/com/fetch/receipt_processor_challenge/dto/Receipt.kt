package com.fetch.receipt_processor_challenge.dto

import jakarta.validation.constraints.Pattern

data class Receipt(
    var id: String?,
    @field:Pattern(regexp = "^[\\w\\s\\-&]+$")
    val retailer: String,
    @field:Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}\$")
    val purchaseDate: String,
    @field:Pattern(regexp = "^\\d{2}:\\d{2}\$")
    val purchaseTime: String,
    @field:Pattern(regexp = "^\\d+\\.\\d{2}$")
    val total: String,
    val items: List<Item>
)

data class Item(
    @field:Pattern(regexp = "^[\\w\\s\\-]+$")
    val shortDescription: String,
    @field:Pattern(regexp = "^\\d+\\.\\d{2}$")
    val price: String
)
