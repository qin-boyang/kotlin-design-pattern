package com.mycompany.myproject.demo.userregister

import kotlin.test.Test

class UserRegisterTest {
    @Test
    fun `test register`() {
        val service = UserService()
        service.register("John")
    }
}