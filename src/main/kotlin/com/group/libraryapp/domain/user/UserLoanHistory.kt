package com.group.libraryapp.domain.user

import javax.persistence.*

@Entity
class UserLoanHistory(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        val user: User,

        var bookName: String,

        var isReturn: Boolean
) {
    fun doReturn() {
        this.isReturn = true
    }
}