package com.example.samvadapp.activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.samvadapp.R
import com.example.samvadapp.ui.theme.SamvadAppTheme
import com.example.samvadapp.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToEvents()
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
        var showProgress: Boolean by remember {
            mutableStateOf(false)
        }
        viewModel.loadingEvent.observe(this, Observer { loadingState->
            showProgress = when(loadingState){
                is LoginViewModel.LoadingEvent.Loading -> {
                    true
                }

                LoginViewModel.LoadingEvent.NotLoading -> {
                    false
                }
            }
        })
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
                        top.linkTo(parent.top, margin = 175.dp)
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
            Button(onClick = { viewModel.loginUser(userName.text, getString(R.string.jwt_token)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(btnLoginAsUser) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(userNameTextField.bottom, margin = 16.dp)
                    }) {

                Text(text = "Login as User")
            }
            Button(onClick = { viewModel.loginUser(userName.text)},
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(btnLoginAsGuest) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(btnLoginAsUser.bottom, margin = 16.dp)
                    }) {

                Text(text = "Login as Guest")
            }
            if (showProgress) {
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(progressBar) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(btnLoginAsGuest.bottom, margin = 16.dp)
                    }
                )
            }

        }

    }
    private fun subscribeToEvents() {

        lifecycleScope.launch {
            viewModel.logInEvent.collect{ event ->
                when(event){
                    is LoginViewModel.LogInEvent.ErrorInputTooShort -> {
                        showToast("Invalid! Enter more than 4 characters")
                    }

                    is LoginViewModel.LogInEvent.ErrorLogin -> {
                        val errorMessage = event.error
                        showToast("Error : $errorMessage")
                    }
                    LoginViewModel.LogInEvent.SuccessLogin -> {
                        showToast("Login Successful!")
                    }
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}


