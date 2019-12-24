package com.interventure.hackathon.numbers.pictures

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.interventure.hackathon.numbers.R
import kotlinx.android.synthetic.main.fragment_pictures.*

class PicturesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.fragment_pictures, null)
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)

        val number = arguments?.getInt(EXTRA_NUMBER) ?: 0
        val viewId = when (number) {
            1 -> R.layout.view_number_one
            2 -> R.layout.view_number_two
            3 -> R.layout.view_number_three
            4 -> R.layout.view_number_four
            5 -> R.layout.view_number_five
            6 -> R.layout.view_number_six
            7 -> R.layout.view_number_seven
            8 -> R.layout.view_number_eight
            9 -> R.layout.view_number_nine
            else -> R.layout.view_number_one
        }

        frame?.addView(LayoutInflater.from(context).inflate(viewId, null))

        var counter = 0
        rootView.findViewById<RelativeLayout>(R.id.stars_container)?.let {
            val childCount = it.childCount
            for (index in 0..childCount) {
                val childView = it.getChildAt(index)
                childView?.setOnClickListener { view ->
                    (view as? ImageView)?.setImageResource(R.drawable.ic_star_clicked)
                    view?.isEnabled = false
                    val clickSound = MediaPlayer.create(context, R.raw.click)
                    clickSound.start()
                    counter++
                    if (counter == number) {
                        rootView.findViewById<ImageView>(R.id.image)?.visibility = View.VISIBLE
                        it.alpha = 0.2f
                    }
                }
            }
        }
    }

    companion object {

        fun newInstance(number: Int): PicturesFragment {
            val picturesFragment = PicturesFragment()
            val bundle = Bundle()
            bundle.putInt(EXTRA_NUMBER, number)
            picturesFragment.arguments = bundle
            return picturesFragment
        }

        const val EXTRA_NUMBER = "extra_number"
    }
}