package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.idan_koren_israeli.heartfit.R

class FragmentSplash : Fragment() {
    private val ARG_ICON_ID = "icon_id"

    private var iconId: Int? = null
    private var onAnimationFinish : (()->Unit)? = null

    private lateinit var iconImage: ImageView
    private lateinit var parent: View



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            iconId = it.getInt(ARG_ICON_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        parent= inflater.inflate(R.layout.fragment_splash, container, false)
        findViews()

        applyIconResource()
        applyAnimation()
        return parent
    }

    private fun findViews(){
        iconImage = parent.findViewById(R.id.splash_IMG_icon);
    }

    private fun applyIconResource(){
        iconImage.setImageDrawable(ContextCompat.getDrawable(parent.context, iconId!!))
    }

    private fun applyAnimation() {
        iconImage.scaleX = (0.55f)
        iconImage.scaleY = (0.55f)
        iconImage.alpha = (0.0f)
        iconImage.animate()
            .alpha(1.0f)
            .scaleX(1.0f)
            .scaleY(1.0f)
            .setDuration(ANIMATION_DURATION)
            .setInterpolator(LinearInterpolator())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator?) {}
                override fun onAnimationEnd(animator: Animator?) {
                    if(onAnimationFinish!=null)
                        onAnimationFinish!!.invoke()
                    else
                        Log.w(javaClass.name,"Animation Finish callback is null")
                }

                override fun onAnimationCancel(animator: Animator?) {}
                override fun onAnimationRepeat(animator: Animator?) {}
            })
    }


    fun setOnAnimationFinishListener(onAnimationFinish: ()->Unit){
        this.onAnimationFinish = onAnimationFinish
    }


    companion object {

        private const val ANIMATION_DURATION:Long = 1500

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param iconId Res Id of app icon.
         * @return A new instance of fragment FragmentSplash.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(iconId: Int) =
            FragmentSplash().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ICON_ID, iconId)
                }
            }
    }
}