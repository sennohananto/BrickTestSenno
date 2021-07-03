package com.example.bricktestsenno

import android.app.Application
import io.onebrick.sdk.CoreBrickSDK
import io.onebrick.sdk.util.Environment

class BaseApp: Application() {
    private val clientId = "f5ca52ca-e78e-4b01-a640-49a74195f410"
    private val clientSecret = "AiYcHzUpDBz5i5bG3vgtMTPsH4Y7Tv"
    private val name = "BRICK"
    private val url = "https://onebrick.io"

    override fun onCreate() {
        super.onCreate()
        CoreBrickSDK.initializedSDK(clientId,clientSecret,name,url, Environment.SANDBOX)
    }

}