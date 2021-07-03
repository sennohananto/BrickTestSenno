package com.example.bricktestsenno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import io.onebrick.sdk.*
import io.onebrick.sdk.model.*
import io.onebrick.sdk.util.Environment

class MainActivity : AppCompatActivity(), ICoreBrickUISDK {
    private lateinit var btnInit: Button
    private lateinit var btnAccessToken: Button
    private lateinit var btnInstitution: Button
    private lateinit var btnAuthUser: Button
    private lateinit var btnListAccount: Button
    private lateinit var btnDemoUi: Button
    private lateinit var btnRequestTokenCredentials: Button

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etInstitutionId: EditText

    private val name = "Binar Academy"
    private val url = "https://sandbox.onebrick.io"

    //Client Senno
    private val clientId = "f5ca52ca-e78e-4b01-a640-49a74195f410"
    private val clientSecret = "AiYcHzUpDBz5i5bG3vgtMTPsH4Y7Tv"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInit = findViewById(R.id.btnInit)
        btnInstitution = findViewById(R.id.btnInstitution)
        btnAuthUser = findViewById(R.id.btnAuthUser)
        btnListAccount = findViewById(R.id.btnListAccount)
        btnDemoUi = findViewById(R.id.btnDemoUi)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etInstitutionId = findViewById(R.id.etInstitutionId)
        btnRequestTokenCredentials = findViewById(R.id.btnRequestTokenCredentials)





        btnInit.setOnClickListener {
            CoreBrickUISDK.initializedUISDK(applicationContext,clientId,clientSecret,name,url,this, Environment.SANDBOX)
        }


        btnRequestTokenCredentials.setOnClickListener {
            CoreBrickSDK.requestTokenCredentials(object:IRequestTokenCredentials{
                override fun success(response: AccessTokenRequest?) {
                    AlertDialog.Builder(this@MainActivity).setTitle("Success Request Token Credentials").setMessage("Client Fullname : ${response?.data?.clientFullName}").setPositiveButton("OK",null)
                }

                override fun error(t: Throwable?) {
                    AlertDialog.Builder(this@MainActivity).setTitle("Error Request Token Credentials").setMessage("Error : ${t.toString()}").setPositiveButton("OK",null)
                }

            })

        }

        btnInstitution.setOnClickListener {
            CoreBrickSDK.listInstitution(object :IRequestInstituion{
                override fun success(response: Institution?) {
                    var listInstitutionString: String = ""
                    response?.data?.forEach {
                        listInstitutionString += "Code : ${it.bankCode} | ${it.bankName} \n"
                    }

                    AlertDialog.Builder(this@MainActivity).setTitle("Success Get List Institution").setMessage(listInstitutionString).setPositiveButton("OK",null)

                }

                override fun error(t: Throwable?) {
                    AlertDialog.Builder(this@MainActivity).setTitle("Error Get List Institution").setMessage("Error : ${t?.message}").setPositiveButton("OK",null)
                }

            })
        }

        btnAuthUser.setOnClickListener {
            CoreBrickSDK.authenticateUser(etUsername.text.toString(),etPassword.text.toString(),etInstitutionId.text.toString(),object:
                    IRequestResponseUserAuth {
                override fun success(response: AuthenticateUserResponse) {
                    //// you need to handle response.status here
                    //// if status response 200 then you can use
                    /// if status response 428 then go to next step
                    when(response.status){
                        200L -> {
                            AlertDialog.Builder(this@MainActivity).setTitle("Success Auth User").setMessage("Response : \nStatus Code : ${response.status}\nMessage : ${response.message}\nData : ${response.data.toString()}").setPositiveButton("OK",null)
                        }

                        428L -> {
                            AlertDialog.Builder(this@MainActivity).setTitle("Success Auth User").setMessage("Response : \nStatus Code : ${response.status}\nMessage : ${response.message}\nData : ${response.data.toString()}").setPositiveButton("OK",null)
                        }

                        else -> {
                            AlertDialog.Builder(this@MainActivity).setTitle("Success Auth User").setMessage("Response : \nStatus Code : ${response.status}\nMessage : ${response.message}\nData : ${response.data.toString()}").setPositiveButton("OK",null)
                        }
                    }
                }

                override fun error(t: Throwable?) {
                    AlertDialog.Builder(this@MainActivity ).setTitle("Failed Auth User").setMessage("Message : ${t?.message}").setPositiveButton("OK",null)
                }

            })
        }

        btnListAccount.setOnClickListener {
            CoreBrickSDK.listAccountUser()
        }

        btnDemoUi.setOnClickListener {
            CoreBrickUISDK.initializedUISDK( applicationContext, clientId, clientSecret, name, url,
                    this, Environment.SANDBOX)
        }
    }

    override fun onTransactionSuccess(transactionResult: AuthenticateUserResponseData) {
        AlertDialog.Builder(this@MainActivity ).setTitle("Transaction Success").setMessage(" ${transactionResult.toString()}").setPositiveButton("OK",null)
    }
}