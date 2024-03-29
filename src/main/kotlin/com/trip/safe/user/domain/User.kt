package com.trip.safe.user.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("user")
class User(
    @Id
    var id: Long = 0,

    val accountId: String,
    val password: String,
    val age: Short
)
