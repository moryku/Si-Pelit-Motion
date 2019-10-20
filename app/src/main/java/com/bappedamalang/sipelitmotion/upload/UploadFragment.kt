package com.bappedamalang.sipelitmotion.upload

import android.Manifest
import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.bappedamalang.sipelitmotion.model.response.ResponseCategory
import com.ezyindustries.pos.api.APIService
import com.ezyindustries.pos.api.RetrofitService
import kotlinx.android.synthetic.main.fragment_upload.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent
import com.bappedamalang.sipelitmotion.model.MCategory
import android.app.ProgressDialog
import android.text.TextUtils
import android.widget.Toast
import com.bappedamalang.sipelitmotion.*
import com.bappedamalang.sipelitmotion.interfaces.SuccessAddKajian
import com.bappedamalang.sipelitmotion.model.response.ResponseSubmitKajian
import com.bappedamalang.sipelitmotion.util.Util.Companion.createPartFromDocumentWithParams
import com.bappedamalang.sipelitmotion.util.Util.Companion.createPartFromImageWithParams
import com.bappedamalang.sipelitmotion.util.Util.Companion.createPartFromString
import id.flwi.util.ActivityUtil
import okhttp3.RequestBody
import java.util.HashMap
import com.vincent.filepicker.Constant
import com.vincent.filepicker.activity.ImagePickActivity
import com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA
import com.vincent.filepicker.activity.NormalFilePickActivity
import com.vincent.filepicker.filter.entity.ImageFile
import com.vincent.filepicker.filter.entity.NormalFile
import org.greenrobot.eventbus.EventBus


class UploadFragment : Fragment(), AdapterView.OnItemSelectedListener {

    var v: View? = null
    private var apiService: APIService? = null
    var category: MCategory? = null
    val REQUEST_FILE_PDF = 1
    val REQUEST_FILE_IMAGE = 2
    val READ_REQUEST_CODE = 3
    var pathDokumen: String? = null
    var pathImage: String? = null
    var categoryAdapter: ArrayAdapter<MCategory>? = null
    private var progressDialog: ProgressDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_upload, container, false)
        apiService = RetrofitService.createService(APIService::class.java)
        progressDialog = ProgressDialog(context!!)
        progressDialog?.setTitle("Upload...")
        initListener()
        getAllCategory()
        return v
    }

    fun getAllCategory() {
        apiService!!.getAllKategory().enqueue(object : Callback<ResponseCategory> {
            override fun onFailure(call: Call<ResponseCategory>, t: Throwable) {
                Log.d("error", t.toString())
            }

            override fun onResponse(
                call: Call<ResponseCategory>,
                response: Response<ResponseCategory>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null && response!!.body()!!.data != null && response!!.body()!!.data.size > 0) {
                        categoryAdapter = ArrayAdapter(
                            context!!,
                            R.layout.support_simple_spinner_dropdown_item,
                            response!!.body()!!.data
                        )
                        v?.spinner?.adapter = categoryAdapter
                    }
                }
            }

        })
    }

    fun initListener() {
        v?.spinner?.onItemSelectedListener = this
        v?.buttonChooseFile?.setOnClickListener {
            val intent4 = Intent(context!!, NormalFilePickActivity::class.java)
            intent4.putExtra(Constant.MAX_NUMBER, 1)
            intent4.putExtra(NormalFilePickActivity.SUFFIX, arrayOf<String>("pdf"))
            startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE)
        }
        v?.buttonChooseImage?.setOnClickListener {
            val intent4 = Intent(context!!, ImagePickActivity::class.java)
            intent4.putExtra(Constant.MAX_NUMBER, 1)
            intent4.putExtra(IS_NEED_CAMERA, false);
            startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_IMAGE)
        }
        v?.submitKajian?.setOnClickListener {
            uploadImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_OK) {
            return;
        } else if (requestCode == Constant.REQUEST_CODE_PICK_FILE) {
            var list = data?.getParcelableArrayListExtra<NormalFile>(Constant.RESULT_PICK_FILE);
            if (list!!.size > 0) {
                pathDokumen = list?.get(0)!!.path
                v?.textChooseFile?.text = list?.get(0)!!.name + ".pdf"
            }
        } else if (requestCode == Constant.REQUEST_CODE_PICK_IMAGE) {
            var list = data?.getParcelableArrayListExtra<ImageFile>(Constant.RESULT_PICK_IMAGE);
            if (list!!.size > 0) {
                pathImage = list?.get(0)!!.path
                v?.textChooseImage?.text = list?.get(0)!!.name
            }
        }
    }


    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        category = categoryAdapter?.getItem(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    fun uploadImage() {
        if (TextUtils.isEmpty(v?.textAbstrak?.text)) {
            v?.textAbstrak?.error = "Abstrak masih kosong"
            return
        }
        if (TextUtils.isEmpty(v?.textJudul?.text)) {
            v?.textJudul?.error = "Judul masih kosong"
            return
        }
        if (TextUtils.isEmpty(v?.textJumlahHal?.text)) {
            v?.textJumlahHal?.error = "Jumlah halaman masih kosong"
            return
        }
        if (TextUtils.isEmpty(pathDokumen)) {
            Toast.makeText(
                context!!,
                "File dokumen masih kosong",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (TextUtils.isEmpty(pathImage)) {
            Toast.makeText(
                context!!,
                "File image masih kosong",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (category == null && TextUtils.isEmpty(category?.id.toString())) {
            Toast.makeText(
                context!!,
                "Kategori masih kosong",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        progressDialog?.show()
        val params = HashMap<String, RequestBody>()
        params["judul"] = createPartFromString(v?.textJudul?.text.toString())
        params["kategori_id"] = createPartFromString(category?.id.toString())
        params["jumlah_hal"] = createPartFromString(v?.textJumlahHal?.text.toString())
        params["user_id"] =
            createPartFromString(ActivityUtil.getSharedPreferenceString(context!!, USER_ID))
        params["abstrak"] = createPartFromString(v?.textAbstrak?.text.toString())

        apiService!!.submitKajian(
            params,
            createPartFromDocumentWithParams(pathDokumen!!, "dokumen"),
            createPartFromImageWithParams(pathImage!!, "image")
        )
            .enqueue(object : Callback<ResponseSubmitKajian> {
                override fun onFailure(call: Call<ResponseSubmitKajian>, t: Throwable) {
                    Toast.makeText(
                        context!!,
                        "Gagal Upload : " + t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    progressDialog?.dismiss()
                }

                override fun onResponse(
                    call: Call<ResponseSubmitKajian>,
                    response: Response<ResponseSubmitKajian>
                ) {
                    progressDialog?.dismiss()
                    if (response.isSuccessful) {
                        if (response.body() != null && response!!.body()!!.data != null) {
                            Toast.makeText(context!!, "Berhasil Upload ", Toast.LENGTH_SHORT).show()
                            EventBus.getDefault().post(SuccessAddKajian());
                            return
                        }
                    }
                    Toast.makeText(context!!, "Gagal Upload ", Toast.LENGTH_SHORT).show()
                }

            })
    }
}