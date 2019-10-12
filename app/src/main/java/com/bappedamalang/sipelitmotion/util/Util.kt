package com.bappedamalang.sipelitmotion.util

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class Util {

    companion object {


        /**
         * Upload Image
         *
         * @param path
         * @return
         */
        fun createPartFromImageWithParams(path: String, nameParams: String): MultipartBody.Part {
            val file = File(path)
            val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
            return MultipartBody.Part.createFormData(nameParams, file.name, reqFile)
        }

        /**
         * Upload Image
         *
         * @param path
         * @return
         */
        fun createPartFromDocumentWithParams(path: String, nameParams: String): MultipartBody.Part {
            val file = File(path)
            val reqFile = RequestBody.create(MediaType.parse("pdf/*"), file)
            return MultipartBody.Part.createFormData(nameParams, file.name, reqFile)
        }

        fun createPartFromString(descriptionString: String?): RequestBody {
            var descriptionString = descriptionString
            if (descriptionString == null) descriptionString = ""
            return RequestBody.create(MediaType.parse("text/plain"), descriptionString)
        }
    }

}