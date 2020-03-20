package com.example.shootbuy_seller

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.shootbuy_seller.Models.UserData.Seller
import com.example.shootbuy_seller.Services.IfSellerExist
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var MY_WRITE_EXTERNAL_STORAGE_CODE = 1111

    private var TAG = "Google Signin"
    private var RC_SIGN_IN = 1000
    private var googleSignInButton: SignInButton? = null
    private var signOutBtn: Button? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var auth: FirebaseAuth

    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var sellerUuid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        signOutBtn = findViewById(R.id.signOutBtn)
        signOutBtn?.setOnClickListener(this)
        googleSignInButton = findViewById(R.id.google_button)
        googleSignInButton?.setOnClickListener(this@MainActivity)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.INTERNET),123)
        pref = getSharedPreferences("SP_Seller_DATA", Context.MODE_PRIVATE)
        sellerUuid = pref!!.getString("UUID", "")


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode===123){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                ifLoggedIn()
            }
        }
    }


    private fun ifLoggedIn() {
        if (sellerUuid != "") {
            startActivity(Intent(this, SellerOrderActivity::class.java))
            finish()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.google_button -> {
                signIn()
            }
            R.id.signOutBtn -> {
                signOut()
            }

        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        mGoogleSignInClient?.signOut()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser
                    Log.d(TAG, "signInWithCredential:success" + user?.email)
                    ifSellerExist(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun ifSellerExist(firebaseUser: FirebaseUser?) {
        editor = pref!!.edit()
        editor!!.putString("UUID", firebaseUser!!.uid).apply()

        val url = IPAddress.ipAddress + "user-data/ifSellerExist/"
        var name = firebaseUser.displayName!!.split(" ").toTypedArray()
        var sellerData = Seller()
        sellerData.sellerUuid = firebaseUser.uid
        sellerData.sellerEmail = firebaseUser.email
        sellerData.sellerFirstName = name[0]
        sellerData.sellerLastName = name[1]

        IfSellerExist(sellerData).execute(url)
        startActivity(Intent(this, SellerOrderActivity::class.java))
        finish()
    }
}
