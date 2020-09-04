package com.fieldfreshmarket.api.model

import java.time.Instant
import javax.persistence.*

@MappedSuperclass
abstract class BaseModel(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(insertable = false, columnDefinition = "bpchar")
    var id: String? = null
) {
    val createdAt: Instant = Instant.now()
    var updatedAt: Instant? = Instant.now()

    @PreUpdate
    fun update() {
        updatedAt = Instant.now()
    }
}
