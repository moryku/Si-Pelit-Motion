package com.bappedamalang.sipelitmotion.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bappedamalang.sipelitmotion.BASE_URL_COVER
import com.bappedamalang.sipelitmotion.BASE_URL_FILE
import com.bappedamalang.sipelitmotion.R
import com.bappedamalang.sipelitmotion.model.MKajian
import com.bappedamalang.sipelitmotion.pdf.PDFActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.concurrent.Callable

class DetailKajianActivity: AppCompatActivity() {

    var data: MKajian? = null
    var isMyDocument: Boolean? = false

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        data = intent.getSerializableExtra("data") as MKajian


        subscribeUI()
    }

    fun subscribeUI(){
        setTitle(data?.judul)
        Glide.with(this)
            .load(BASE_URL_COVER + data?.image)
            .into(image)
        textAuthor.text = data?.name
        categoryText.text = data?.kategori
        countHal.text = data?.jumlah_hal.toString()
        deskripsiProduk.text = data?.abstrak
        detailButton.setOnClickListener {
            startActivity(Intent(this, PDFActivity::class.java).putExtra("data", data?.url_kajian))
        }
    }
}