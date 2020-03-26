package com.example.shootbuy_seller

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.shootbuy_seller.Models.ProductOrder.SellerOrder
import com.example.shootbuy_seller.Services.ConfirmSellerOrder
import com.example.shootbuy_seller.Services.Multipost
import com.github.gcacace.signaturepad.views.SignaturePad
import kotlinx.android.synthetic.main.activity_signature.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class SignatureActivity : AppCompatActivity() {
    private var orderId: Int? = null
    private var mSignaturePad: SignaturePad? = null
    private var saveSignatureBtn: LinearLayout? = null
    private var clearSignatureBtn: LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signature)

        mSignaturePad = findViewById(R.id.signaturePad)
        saveSignatureBtn = findViewById(R.id.saveSignatureBtn)
        clearSignatureBtn = findViewById(R.id.clearSignatureBtn)

        orderId = intent.getIntExtra("orderId", 0)
        saveSignatureBtn!!.isEnabled = false
        clearSignatureBtn!!.isEnabled = false
        mSignaturePad!!.setOnSignedListener(object : SignaturePad.OnSignedListener {

            override fun onStartSigning() {
                //Event triggered when the pad is touched
            }

            override fun onSigned() {
                //Event triggered when the pad is signed
                saveSignatureBtn!!.isEnabled = true
                clearSignatureBtn!!.isEnabled = true
            }

            override fun onClear() {
                //Event triggered when the pad is cleared
                saveSignatureBtn!!.isEnabled = false
                clearSignatureBtn!!.isEnabled = false
            }
        })

        saveSignatureBtn!!.setOnClickListener {
            var f = saveBitmapToImage()
            var url = IPAddress.ipAddress + "product-order/signatureUpload/"

//            var url = IPAddress.ipAddress + "product-order/confirmSellerOrder/"
//            var data = SellerOrder(null, orderId)
//            ConfirmSellerOrder(data).execute(url)

            Multipost(signaturePad.signatureBitmap, f,orderId).execute(url)
            var intent = Intent(this, SellerOrderActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finishAffinity()

        }
        clearSignatureBtn!!.setOnClickListener {
            signaturePad.clear()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = MenuInflater(this).inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> {
                val intent = Intent(this, MainActivity::class.java)
//            Log.d("Address",oldHolder!!.address.text.toString())
                intent.putExtra("Logout", "Logout")
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveBitmapToImage(): File {
        var root = Environment.getExternalStorageDirectory().absolutePath + "/signatures"
        var myDir = File(root)
        if (!myDir.exists()) {
            myDir.mkdirs()
        }
//        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//        val formatter = SimpleDateFormat("ddMMyyyyHHmmss")
//        val formattedDate =
//            formatter.format(parser.parse(Date().toString())!!)

        val parser = SimpleDateFormat("yyyyMMddHHmmss")
        val formattedDate = parser.format(Date()).toString()


        var fname = formattedDate + ".jpg"
        var file = File(myDir, fname)
        try {
            var out = FileOutputStream(file)
            mSignaturePad!!.signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }


}




