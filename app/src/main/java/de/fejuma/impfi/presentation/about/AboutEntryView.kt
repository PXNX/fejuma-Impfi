package de.fejuma.impfi.presentation.about

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import de.fejuma.impfi.R

class AboutEntryView (context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.about_entry, this)

//        val imageView: ImageView = findViewById(R.id.image)
//        val textView: TextView = findViewById(R.id.caption)

//        val attributes = context.obtainStyledAttributes(attrs, R.styleable.BenefitView)
//        imageView.setImageDrawable(attributes.getDrawable(R.styleable.BenefitView_image))
//        textView.text = attributes.getString(R.styleable.BenefitView_text)
//        attributes.recycle()

    }
}