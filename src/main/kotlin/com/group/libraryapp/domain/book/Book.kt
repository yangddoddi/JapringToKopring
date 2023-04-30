package com.group.libraryapp.domain.book

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.*
import javax.persistence.Id

@Entity
class Book(
        @Id
        @GeneratedValue(strategy = IDENTITY)
        val id: Long? = null,
        var name: String
) {

    init {
        if (name.isBlank()) throw IllegalArgumentException("이름은 null일 수 없습니다.")
    }
}