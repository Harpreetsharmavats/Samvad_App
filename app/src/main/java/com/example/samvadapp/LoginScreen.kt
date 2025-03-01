package com.example.samvadapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.samvadapp.ui.theme.SamvadAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SamvadAppTheme {
                //LoginScreen()
            }
        }
    }
    /*@Composable
    fun LoginScreen() {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(start = 35.dp, end = 35.dp)){
            var(logo,userNameTextField,btnLoginAsUser,btnLoginAsGuest,progressBar) = createRfs()
            Image(painter = R.drawable., contentDescription = )

        }

    }*/

}


