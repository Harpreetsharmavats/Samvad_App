package com.example.samvadapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.samvadapp.ui.theme.SamvadAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SamvadAppTheme {
                LoginScreen()
            }
        }
    }
@Preview(showBackground = true)
    @Composable
    fun LoginScreen() {
        var userName by remember {
            mutableStateOf(TextFieldValue(""))
        }
        var showProgress : Boolean by remember {
            mutableStateOf(false)
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 35.dp, end = 35.dp)
        ) {

            val (logo, userNameTextField, btnLoginAsUser, btnLoginAsGuest, progressBar) = createRefs()
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo",
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .constrainAs(logo) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top, margin = 100.dp)
                    })
            OutlinedTextField(
                value = userName,
                onValueChange = { newValue -> userName = newValue },
                label = {
                    Text(

                        text = "Enter the UserName"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(userNameTextField) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(logo.bottom, margin = 32.dp)
                    },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(btnLoginAsUser) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(userNameTextField.bottom, margin = 16.dp)
                    }) {

                Text(text = "Login as User")
            }
            Button(onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(btnLoginAsGuest) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(btnLoginAsUser.bottom, margin = 16.dp)
                    }) {

                Text(text = "Login as Guest")
            }
            if (showProgress){
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(progressBar){
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(btnLoginAsGuest.bottom , margin = 16.dp)
                    }
                )
            }

        }

    }

}


