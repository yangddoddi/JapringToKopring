package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.loanhistory.JavaUserLoanHistory
import java.util.NoSuchElementException
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        var name: String,
        val age: Int,
        @OneToMany(mappedBy = "user", cascade = [CascadeType.PERSIST])
        val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf()
) {

    fun updateName(name: String) {
        this.name = name
    }

    fun loanBook(book: Book) {
        this.userLoanHistories.add(UserLoanHistory(user = this, bookName = book.name, isReturn = false))
    }

    fun returnBook(bookName: String) {
        this.userLoanHistories
                .firstOrNull { userLoanHistory ->
                    userLoanHistory.bookName == bookName
                }
                ?.doReturn() ?: throw NoSuchElementException()
    }
}