package com.interventure.hackathon.numbers

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.interventure.hackathon.numbers.drawnumber.DrawNumberActivity
import com.interventure.hackathon.numbers.pictures.PicturesFragment
import com.interventure.hackathon.numbers.scratch.ScratchActivity
import kotlinx.android.synthetic.main.number_details_fragment.*

class NumberDetailsFragment(private val number: CharSequence, @ColorInt val color: Int) : Fragment() {

    companion object {
        fun newInstance(number: CharSequence, @ColorInt color: Int) = NumberDetailsFragment(number, color)
    }

    private lateinit var viewModel: NumberDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.number_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NumberDetailsViewModel::class.java)
        // TODO: Use the ViewModel

        numberText.text = number
        numberText.setTextColor(color)
        numberTextCardView.setOnClickListener {
            val intent = Intent(activity, DrawNumberActivity::class.java)
            intent.putExtra(DrawNumberActivity.NUMBER, number.toString().toInt())
            intent.putExtra(DrawNumberActivity.COLOR, color)
            startActivity(intent)
        }
        ogrebiCardView.setOnClickListener {
            val intent = Intent(activity, ScratchActivity::class.java)
            intent.putExtra(DrawNumberActivity.NUMBER, number.toString().toInt())
            startActivity(intent)
        }
        starCardView.setOnClickListener {
            val fragment = PicturesFragment.newInstance(number.toString().toInt())
            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, fragment)
                ?.addToBackStack(null)?.commitAllowingStateLoss()
        }
    }

}
