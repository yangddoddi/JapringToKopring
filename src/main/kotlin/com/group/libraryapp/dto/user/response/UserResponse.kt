package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User
import org.apache.coyote.Response

data class UserResponse(val id: Long,
                   val name: String,
                   val age: Int) {

    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(user.id!!, user.name, user.age)
        }
    }
}