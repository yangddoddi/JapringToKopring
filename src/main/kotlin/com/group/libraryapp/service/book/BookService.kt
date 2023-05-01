package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class BookService(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
) {
    fun saveBook(request: BookRequest) {
        bookRepository.save(Book(name = request.name))
    }

    fun loanBook(request: BookLoanRequest) {
        val book = bookRepository
            .findByName(request.bookName) ?: fail()

        if (userLoanHistoryRepository
            .findByBookNameAndIsReturn(request.bookName, false) != null) throw IllegalArgumentException()

        val user = userRepository
            .findByName(request.userName) ?: fail()

        user.loanBook(book)
    }

    fun returnBook(request: BookReturnRequest) {
        val user = userRepository
            .findByName(request.userName) ?: fail()

        user.returnBook(request.bookName)
    }
}