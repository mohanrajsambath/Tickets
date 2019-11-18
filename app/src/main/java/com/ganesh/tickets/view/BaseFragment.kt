package com.ganesh.tickets.view

import android.widget.Toast
import androidx.fragment.app.Fragment


open class BaseFragment : Fragment() {

    fun showMessage(message:String){
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show()
    }

}