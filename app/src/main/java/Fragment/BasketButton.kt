package Fragment

import Main.MainViewModel
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.a24mo.R

class CustomBottomButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private  lateinit var vm : MainViewModel

    private var customText: TextView
    init {
        val v = View.inflate(context, R.layout.shopping_basket_button, this)

        customText = v.findViewById(R.id.shopping_count)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomBottomButton,
            0,0
        ).apply {
            try {
                setText(getString(R.styleable.CustomBottomButton_customText))
                setTextColor(getColor(R.styleable.CustomBottomButton_customTextColor, 0))
            } finally {
                recycle()
            }

        }
    }

    fun setText(text: String?) {
        customText.text = text
        onRefresh()
    }

    fun setTextColor(color: Int){
        customText.setTextColor(color)
    }

    private fun onRefresh() {
        invalidate()
        requestLayout()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
    }
}



