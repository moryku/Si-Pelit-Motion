package com.bappedamalang.sipelitmotion.pdf

import android.Manifest
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bappedamalang.sipelitmotion.BASE_URL_FILE
import com.bappedamalang.sipelitmotion.R
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.krishna.fileloader.builder.FileLoaderBuilder
import kotlinx.android.synthetic.main.activity_detail_pdf.*
import java.io.File
import java.util.concurrent.Callable
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class PDFActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    var MY_REQUEST_CODE_WRITE_EXTERNAL = 1
    var fileName = ""
    var fileLoader: FileLoaderBuilder? = null
    var directoryFileName = ""
    var filePdf: File? = null
    private var mRequestPermissionHandler: RequestPermissionHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pdf)
        fileName = intent.getStringExtra("data")
        directoryFileName = this.getExternalFilesDir("kajian").toString() + File.separator + fileName
        filePdf = File(directoryFileName)
        mRequestPermissionHandler = RequestPermissionHandler()
        handlePermission()
    }

    fun checkPDF() {
        if (filePdf != null && filePdf!!.exists()) {
            showPDF()
        } else {
            downloadDataPDF()
        }
    }

    fun downloadDataPDF(){
        PDFHelper(
            this,
            BASE_URL_FILE + intent.getStringExtra("data"),
            Callable<Void> {
                //Callable function if download is successful
                showPDF()
                null
            },
            Callable<Void> {
                //Callable function if download isn't successful
                showError()
                null
            })
    }

    fun showPDF() {
        //Loading the PDF
        pdfView.fromFile(filePdf)
            .defaultPage(0)
            .enableAnnotationRendering(true)
            .scrollHandle(DefaultScrollHandle(this))
            .load()
    }

    fun showError(){
        Toast.makeText(this, "Error downloading ", Toast.LENGTH_SHORT).show();
    }

    private fun handlePermission() {
        mRequestPermissionHandler?.requestPermission(
            this,
            arrayOf<String>(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            123,
            object : RequestPermissionHandler.RequestPermissionListener {
                override fun onSuccess() {
                    checkPDF()
                }

                override fun onFailed() {
                    Toast.makeText(
                        this@PDFActivity,
                        "request permission failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mRequestPermissionHandler?.onRequestPermissionsResult(
            requestCode, permissions,
            grantResults
        )
    }

}