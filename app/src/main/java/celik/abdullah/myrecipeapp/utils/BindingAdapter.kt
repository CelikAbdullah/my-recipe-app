package celik.abdullah.myrecipeapp.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestManager

@BindingAdapter(value=["app:imageToImageView", "app:requestManager"], requireAll = true)
fun bindImageToImageView(imageView: ImageView, data: String?, requestManager: RequestManager?){
    data?.let {
        requestManager?.load(it)?.into(imageView)
    }
}

@BindingAdapter("textToTextView")
fun bindTextToTextView(textView: TextView, data: String?){
    data?.let {
        textView.text = it
    }
}

@BindingAdapter("ratingToTextView")
fun bindRatingToTextView(textView: TextView, data:Long?){
    data?.let{
        textView.text = it.toString()
    }
}