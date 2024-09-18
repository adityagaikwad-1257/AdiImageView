package com.ag.adiimageview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class AdiImageView: FrameLayout {

    // attributes
    private var cornerRadius: Int = 0
    private var loadingColor: Int = android.R.color.darker_gray
    private var errorSrc: Drawable? = AppCompatResources.getDrawable(context, R.drawable.img_error_src)
    private var progressTintMode: Int = context.getColor(android.R.color.white)
    private var srcDrawable: Drawable? = null

    // views
    private var imageView: ImageView? = null
    private var progressView: ProgressBar? = null
    private var cardView: CardView? = null

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ){
        setAttributes(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    ) {
        setAttributes(attrs)
    }

    private fun setAttributes(attrs: AttributeSet?){
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.AdiImageView)

        cornerRadius = attributes.getDimensionPixelSize(R.styleable.AdiImageView_imageCornerRadius, 0)
        errorSrc = attributes.getDrawable(R.styleable.AdiImageView_errorSrc)
        progressTintMode = attributes.getColor(R.styleable.AdiImageView_android_progressTintMode, progressTintMode)
        srcDrawable = attributes.getDrawable(R.styleable.AdiImageView_srcCompat)

        inflateView()

        attributes.recycle()
    }

    private fun inflateView() {
        val view = inflate(context, R.layout.layout_adi_imageview, this)

        imageView = view.findViewById(R.id.adi_image)
        progressView = view.findViewById(R.id.adi_progress)
        cardView = view.findViewById(R.id.adi_card)

        progressView?.indeterminateTintList = ColorStateList.valueOf(progressTintMode)
        cardView?.radius = cornerRadius.toFloat()
        imageView?.setImageDrawable(srcDrawable)
    }

    fun loadImage(@DrawableRes resId: Int){
        imageView?.setImageResource(resId)
    }

    fun loadImage(drawable: Drawable?){
        imageView?.setImageDrawable(drawable)
    }

    fun loadImage(uri: Uri){
        imageView?.setImageURI(uri)
    }

    fun loadImage(url: String?, successListener: (() -> Unit)? = null) {
        imageView?.let {

            progressView?.show()

            Glide.with(context.applicationContext)
                .load(url)
                .addListener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressView?.hide()
                        imageView?.setImageDrawable(errorSrc)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressView?.hide()
                        successListener?.invoke()
                        return false
                    }
                })
                .placeholder(loadingColor)
                .into(it)
        }
    }
}