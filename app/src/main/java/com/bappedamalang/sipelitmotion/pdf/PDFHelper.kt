package com.bappedamalang.sipelitmotion.pdf

import android.content.Context
import androidx.annotation.NonNull

import com.ezyindustries.pos.api.APIService
import com.ezyindustries.pos.api.RetrofitService

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.Callable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Environment.DIRECTORY_DOCUMENTS
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.nio.file.Files.exists
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.net.toUri


/**
 * Created by Amalip on 8/22/2017.
 */

class PDFHelper(
    private val ctx: Context, //E.g.:"rules.pdf"
    private val fileName: String, //Functions that will be called if the PDF was downloaded correctly or not
    private val downloaded: Callable<*>,
    private val error: Callable<*>
) {
    //Retrofit service
    private var pdfServices: APIService? = null

    init {
        pdfServices = RetrofitService.createService(APIService::class.java)
        getPDF()
    }

    //Method to get PDF
    private fun getPDF() {
        //Make the request to download the PDF
        val call = pdfServices!!.downloadPDF(fileName)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(@NonNull call: Call<ResponseBody>, @NonNull response: Response<ResponseBody>) {
                //Check if the download was successful
                if (response.isSuccessful)
                    result(if (writeResponseBodyToDisk(response.body()!!)) downloaded else error)
                else
                    result(error)
            }

            override fun onFailure(@NonNull call: Call<ResponseBody>, @NonNull t: Throwable) {
                result(error)
            }
        })
    }

    //Method to save the PDF
    /*
     * body = The PDF stream
     * */
    private fun writeResponseBodyToDisk(body: ResponseBody): Boolean {
        try {
            val directory = File(ctx.getExternalFilesDir("kajian").toString())
            directory.mkdirs()
            val name = fileName.substring(fileName.lastIndexOf('/') + 1);
            var directoryFileName = ctx.getExternalFilesDir("kajian").toString() + File.separator + name
            //Making the file and pathname where it will be saved
            val PDFFile = File(
                directoryFileName
            )

//            if (!PDFFile.exists()) {
//                try {
//                    PDFFile.createNewFile()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                    return false
//                }
//
//            }
//
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                //Reading and writing the file
                val fileReader = ByteArray(4096)
                inputStream = body.byteStream()
                outputStream = FileOutputStream(PDFFile)
                while (true) {
                    val read = inputStream!!.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                }
                //If all ok then return true else false
                outputStream.flush()
                return true
            } catch (e: IOException) {
                return false
            } finally {
                //Close the reader and writer
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            return false
        }

    }

    //Method to execute the result function
    /*
     * fun = The function to execute
     * */
    private fun <T> result(`fun`: Callable<T>) {
        try {
            `fun`.call()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}