package ru.netology.statsview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.R
import ru.netology.statsview.ui.Statsview


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Statsview>(R.id.statsView).data = listOf(
            50F,
            50F,
            50F,
            50F,
        )
    }
}