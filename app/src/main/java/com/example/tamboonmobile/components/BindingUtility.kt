package com.example.tamboonmobile.components

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.tamboonmobile.R

@BindingAdapter("imageUrl", "placeHolder", requireAll = false)
fun setImageUrl(view: ImageView, imageUrl: String?, placeholder: Drawable?) {
    var glideImageUrl = imageUrl
    var mPlaceholder = placeholder
    if (imageUrl == null) {
        glideImageUrl = ""
    }
    if (placeholder == null) {
        mPlaceholder = ContextCompat.getDrawable(view.context, R.drawable.ic_group_24)
    }
    Glide.with(view.context)
        .load(glideImageUrl)
        .centerCrop()
        .placeholder(mPlaceholder)
        .dontAnimate()
        .into(view)
}