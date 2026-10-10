package ru.blank.mobileappcore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.android.ext.android.inject
import ru.blank.mobileappcore.navigation.api.GraphBuilder
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val graphBuilder: GraphBuilder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(
                graphBuilder = graphBuilder,
            )
        }
    }
}

