package com.example.shareroutine.data.model

data class Like (
    val likeId: Int,
    ) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "likeId" to likeId

        )
    }
}

