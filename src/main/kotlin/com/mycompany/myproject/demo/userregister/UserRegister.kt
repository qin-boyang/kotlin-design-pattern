package com.mycompany.myproject.demo.userregister

sealed class UserRegisterListener {
    companion object {
        fun getListeners(): List<UserRegisterListener> {
            return listOf(
                EmailListener(),
                SmsListener(),
                ScoreListener()
            )
        }
    }

    abstract fun onRegister(event: UserRegisterEvent)

    class EmailListener: UserRegisterListener() {
        override fun onRegister(event: UserRegisterEvent) {
            println("Sending email")
        }
    }

    class SmsListener: UserRegisterListener() {
        override fun onRegister(event: UserRegisterEvent) {
            println("Sending SMS")
        }
    }

    class ScoreListener: UserRegisterListener() {
        override fun onRegister(event: UserRegisterEvent) {
            println("Printing score")
        }
    }
}

data class UserRegisterEvent(val userName: String)

class UserService {
    fun register(userName: String) {
        println("Register Event")
        publishEvent(UserRegisterEvent(userName))
    }

    fun publishEvent(event: UserRegisterEvent) {
        println("Publish Event")
        for (listener in UserRegisterListener.getListeners()) {
            listener.onRegister(event)
        }
    }
}