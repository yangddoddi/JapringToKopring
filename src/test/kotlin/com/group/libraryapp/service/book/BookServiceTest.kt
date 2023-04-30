package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.JavaBook
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
open class BookServiceTest @Autowired constructor(
        val bookService: BookService,
        val bookRepository: BookRepository,
        val userRepository: UserRepository,
        val userLoanHistoryRepository: UserLoanHistoryRepository
) {

    private lateinit var user1: User;
    private lateinit var user2: User;

    @BeforeEach
    fun init() {
        user1 = User("양은찬", 20)
        user2 = User("김만식", 30)
        userRepository.save(user1)
        userRepository.save(user2)
    }

    @Test
    @DisplayName("도서 저장 테스트")
    fun saveBookTest() {
        // given
        val bookRequest = BookRequest("clean code")

        // when
        bookService.saveBook(bookRequest)

        // then
        assertThat(bookRepository.findAll().size).isEqualTo(1)
        assertThat(bookRepository.findAll().get(0).name).isEqualTo("clean code")
    }

    @Test
    @DisplayName("도서 대출 실패 테스트")
    fun loanBookTest() {
        // given
        val savedBook = bookRepository.save(Book(name = "cleanCode"))
        val request1 = BookLoanRequest(user1.name, savedBook.name)
        val request2 = BookLoanRequest(user2.name, savedBook.name)
        bookService.loanBook(request1)

        // when // then
        assertThatThrownBy { bookService.loanBook(request2) }
    }

    @Test
    @DisplayName("도서 대출 성공 테스트")
    fun loanBookTest2() {
        // given
        val savedBook = bookRepository.save(Book(name = "cleanCode"))
        val request1 = BookLoanRequest(user1.name, savedBook.name)

        // when
        bookService.loanBook(request1)

        // then
        assertThat(userLoanHistoryRepository.findAll().size).isEqualTo(1)
        assertThat(userLoanHistoryRepository.findAll().get(0).bookName).isEqualTo(savedBook.name)
    }

    @Test
    @DisplayName("도서 반납 테스트")
    fun returnBookTest() {
        // given
        val savedBook = bookRepository.save(Book(name = "cleanCode"))
        val request1 = BookLoanRequest(user1.name, savedBook.name)
        bookService.loanBook(request1)

        // when
        bookService.returnBook(BookReturnRequest(request1.userName, request1.bookName))

        // then
        assertThat(userLoanHistoryRepository.findAll().get(0).isReturn).isEqualTo(true)
    }
}