package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserResponse
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
open class UserServiceTest @Autowired constructor(
        private val userRepository: UserRepository,
        private val userService: UserService
){

    @Test
    @DisplayName("유저 저장 테스트")
    fun saveTest() {
        // given
        val userCreateRequest = UserCreateRequest("양은찬", 20)

        // when
        userService.saveUser(userCreateRequest)

        // then
        assertThat(userRepository.findAll().size)
                .isEqualTo(1)

        assertThat(userRepository.findAll().get(0).name)
                .isEqualTo("양은찬")
    }

    @Test
    @DisplayName("유저 조회 테스트")
    fun getUserTest() {
        // given
        val savedUser1 = userRepository.save(User("양은찬", 20))
        val savedUser2 = userRepository.save(User("김민식", 30))

        // when
        val result = userService.getUsers()

        // then
        assertThat(result.size)
                .isEqualTo(2)

        assertThat(result)
                .extracting("name")
                .containsExactlyInAnyOrder("양은찬", "김민식")
    }

    @Test
    @DisplayName("유저 이름 수정 테스트")
    fun updateUserTest() {
        // given
        val savedUser = userRepository.save(User("양은찬", 20))

        // when
        userService.updateUserName(UserUpdateRequest(savedUser.id, "김만수"))

        // then
        val updatedUser = userRepository.findById(savedUser.id)
        assertThat(savedUser.name).isEqualTo("김만수")
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    fun deleteUserTest() {
        // given
        val savedUser = userRepository.save(User("양은찬", 20))

        // when
        userService.deleteUser(savedUser.name)

        // then
        val updatedUser = userRepository.findById(savedUser.id)
        assertThat(updatedUser).isEmpty
    }
}