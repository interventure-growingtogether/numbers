package com.interventure.hackathon.numbers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.iterator

import kotlinx.android.synthetic.main.grid_fragment.*

class NumbersGridFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() =
            NumbersGridFragment()
    }

    private lateinit var viewModel: NumbersGridViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.grid_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NumbersGridViewModel::class.java)
        // TODO: Use the ViewModel
        for (view in choice_grid.iterator()) {
            view.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {

    }

}
