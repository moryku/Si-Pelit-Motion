package com.bappedamalang.sipelitmotion.profil

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bappedamalang.sipelitmotion.*
import com.bappedamalang.sipelitmotion.auth.LoginActivity
import  id.flwi.util.ActivityUtil
import kotlinx.android.synthetic.main.fragment_profil.view.*

class ProfilFragment : Fragment() {

    var v: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profil, container, false)
        v?.nameText?.text = ActivityUtil.getSharedPreferenceString(context!!, NAME)
        v?.noHpText?.text = ActivityUtil.getSharedPreferenceString(context!!, NO_HP)
        v?.institusiText?.text = ActivityUtil.getSharedPreferenceString(context!!, INSTITUSI)
        v?.alamatText?.text = ActivityUtil.getSharedPreferenceString(context!!, ADDRESS)
        v?.emailText?.text = ActivityUtil.getSharedPreferenceString(context!!, EMAIL)
        v?.logoutButton?.setOnClickListener {
            ActivityUtil.setSharedPreference(context!!, TOKEN, "")
            ActivityUtil.setSharedPreference(context!!, EMAIL, "")
            ActivityUtil.setSharedPreference(context!!, NAME, "")
            ActivityUtil.setSharedPreference(context!!, ADDRESS, "")
            ActivityUtil.setSharedPreference(context!!, INSTITUSI, "")
            ActivityUtil.setSharedPreference(context!!, NO_HP, "")
            ActivityUtil.setSharedPreference(context!!, USER_ID, "")
            startActivity(Intent(context!!, LoginActivity::class.java))
            (context as Activity).finish();
        }
        return v
    }
}