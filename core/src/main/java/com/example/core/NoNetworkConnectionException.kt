package com.example.core

import java.io.IOException

class NoNetworkConnectionException : IOException() {

    override val message: String
        get() = "No internet connection"
}