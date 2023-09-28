package com.reznikov.webview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.reznikov.webview.databinding.ActivityStudScreenBinding

class StubScreen : AppCompatActivity() {
    private lateinit var binding: ActivityStudScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDropDownList()
    }

    @SuppressLint("DiscouragedApi")
    private fun initDropDownList() {
        with(binding) {
            val scheduleView = listOf(
                breakfast,
                lunch,
                snack,
                dinner,
            )

            val breakfastList = listOf(
                getString(R.string.breakfast_meal),
                getString(R.string.breakfast_meal2),
                getString(R.string.breakfast_meal3),
                getString(R.string.breakfast_meal4),
                getString(R.string.breakfast_meal5),
                getString(R.string.breakfast_meal6),
                getString(R.string.breakfast_meal7),
            )

            val lunchList = listOf(
                getString(R.string.lunch_meal),
                getString(R.string.lunch_meal2),
                getString(R.string.lunch_meal3),
                getString(R.string.lunch_meal4),
                getString(R.string.lunch_meal5),
                getString(R.string.lunch_meal6),
                getString(R.string.lunch_meal7),
            )

            val snackList = listOf(
                getString(R.string.snack_meal),
                getString(R.string.snack_meal2),
                getString(R.string.snack_meal3),
                getString(R.string.snack_meal4),
                getString(R.string.snack_meal5),
                getString(R.string.snack_meal6),
                getString(R.string.snack_meal7),
            )

            val dinnerList = listOf(
                getString(R.string.dinner_meal),
                getString(R.string.dinner_meal2),
                getString(R.string.dinner_meal3),
                getString(R.string.dinner_meal4),
                getString(R.string.dinner_meal5),
                getString(R.string.dinner_meal6),
                getString(R.string.dinner_meal7),
            )

            val schedule = listOf(
                breakfastList,
                lunchList,
                snackList,
                dinnerList
            )

            var selectedTextView = flexboxLayoutDaysOfTheWeek.getChildAt(0)

            for (i in 0 until flexboxLayoutDaysOfTheWeek.childCount) {
                val textView = flexboxLayoutDaysOfTheWeek.getChildAt(i)
                if (textView is TextView) {
                    textView.setOnClickListener {
                        if (selectedTextView != null) {
                            selectedTextView.setBackgroundResource(R.drawable.circle_background)
                        }

                        textView.setBackgroundResource(R.drawable.circle_background_active)

                        selectedTextView = textView

                        schedule.forEachIndexed { index, _ ->
                            scheduleView[index].text = schedule[index][i]
                        }
                    }
                }

            }
        }
    }
}