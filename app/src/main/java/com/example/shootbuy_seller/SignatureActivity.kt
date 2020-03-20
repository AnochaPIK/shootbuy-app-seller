package com.example.shootbuy_seller

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.widget.Button
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


class SignatureActivity : AppCompatActivity() {
    private var orderId: Int? = null
    private var mSignaturePad: SignaturePad? = null
    private var saveSignatureBtn: Button? = null
    private var clearSignatureBtn: Button? = null


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
            var url = IPAddress.ipAddress + "product-order/confirmSellerOrder/"
            var data = SellerOrder(null, orderId)
            ConfirmSellerOrder(data).execute(url)
            var intent = Intent(this, SellerOrderActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
            Multipost(f).execute(url)
        }
        clearSignatureBtn!!.setOnClickListener {
            signaturePad.clear()
        }
    }

    private fun saveBitmapToImage(): File {
        var root = Environment.getExternalStorageDirectory().absolutePath + "/signatures"
        var myDir = File(root)
        if (!myDir.exists()) {
            myDir.mkdirs()
        }
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("ddMMyyyyHHmmss")
        val formattedDate =
            formatter.format(parser.parse(LocalDateTime.now().toString())!!)
        var fname = formattedDate + ".jpg"
        var file = File(myDir, fname)
        try {
            var out = FileOutputStream(file)
            mSignaturePad!!.signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }


}




