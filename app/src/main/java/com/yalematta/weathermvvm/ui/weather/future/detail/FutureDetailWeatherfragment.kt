package com.yalematta.weathermvvm.ui.weather.future.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.yalematta.weathermvvm.R

class FutureDetailWeatherfragment : Fragment() {

    companion object {
        fun newInstance() =
            FutureDetailWeatherfragment()
    }

    private lateinit var viewModel: FutureDetailWeatherfragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weatherfragment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this).get(FutureDetailWeatherfragmentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
