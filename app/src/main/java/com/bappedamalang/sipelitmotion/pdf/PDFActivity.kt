package com.bappedamalang.sipelitmotion.pdf

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bappedamalang.sipelitmotion.R
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.*
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.builder.FileLoaderBuilder
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import kotlinx.android.synthetic.main.activity_detail_pdf.*
import java.io.File

class PDFActivity : AppCompatActivity() {

    lateinit var pdfView: PDFView
    lateinit var progressBar: ProgressBar
    var MY_REQUEST_CODE_WRITE_EXTERNAL = 1
    var fileName = ""
    var fileLoader: FileLoaderBuilder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pdf)
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(intent.getStringExtra("data"));
    }


}