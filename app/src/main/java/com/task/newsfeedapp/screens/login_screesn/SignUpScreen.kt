package com.task.newsfeedapp.screens.login_screesn

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.task.newsfeedapp.base.BaseViewModel
import com.task.newsfeedapp.model.UserProfile
import com.task.newsfeedapp.mvvm.repository.ProfileRepository
import com.task.newsfeedapp.navigation.OnboardingNavigationObject
import com.task.newsfeedapp.utils.state.AuthState
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavController, 
    authViewModel: BaseViewModel,
    profileRepository: ProfileRepository
) {
    var username by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var dobMonth by remember { mutableStateOf("") }
    var dobDay by remember { mutableStateOf("") }
    var dobYear by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var agreeToTerms by remember { mutableStateOf(false) }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticate -> {
                val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
                if (currentUser != null) {
                    val profile = UserProfile(
                        uid = currentUser.uid,
                        displayName = fullName,
                        email = email,
                        username = username,
                        fullName = fullName,
                        country = country,
                        phoneNumber = phoneNumber,
                        gender = gender,
                        dateOfBirth = "$dobMonth/$dobDay/$dobYear"
                    )
                    coroutineScope.launch {
                        profileRepository.saveUserProfile(profile)
                        navController.navigate(OnboardingNavigationObject.BOTTOM_SHEET_SCREEN) {
                            popUpTo(OnboardingNavigationObject.SIGNUP_SCREEN) { inclusive = true }
                        }
                    }
                }
            }
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_LONG
            ).show()

            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFEBF5FF))
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val path = Path().apply {
                        lineTo(0f, size.height * 0.7f)
                        quadraticTo(
                            size.width * 0.25f, size.height * 0.6f,
                            size.width * 0.5f, size.height * 0.8f
                        )
                        quadraticTo(
                            size.width * 0.75f, size.height,
                            size.width, size.height * 0.8f
                        )
                        lineTo(size.width, 0f)
                        close()
                    }
                    drawPath(path, color = Color(0xFF1E88E5))
                }
                
                Text(
                    text = "Registration form",
                    modifier = Modifier.padding(start = 32.dp, top = 60.dp),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RegistrationTextField(value = username, onValueChange = { username = it }, placeholder = "Username")
                RegistrationTextField(value = fullName, onValueChange = { fullName = it }, placeholder = "Full name")
                RegistrationTextField(value = country, onValueChange = { country = it }, placeholder = "Country")
                RegistrationTextField(value = email, onValueChange = { email = it }, placeholder = "E-mail")
                RegistrationTextField(value = phoneNumber, onValueChange = { phoneNumber = it }, placeholder = "Phone number")

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Gender", color = Color(0xFF1E88E5), modifier = Modifier.width(80.dp))
                    GenderButton("Male", gender == "Male") { gender = "Male" }
                    Spacer(Modifier.width(12.dp))
                    GenderButton("Female", gender == "Female") { gender = "Female" }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Date of birth", color = Color(0xFF1E88E5), modifier = Modifier.width(100.dp))
                    DobField(dobMonth, { if(it.length <= 2) dobMonth = it }, "MM", Modifier.weight(1f))
                    Spacer(Modifier.width(8.dp))
                    DobField(dobDay, { if(it.length <= 2) dobDay = it }, "DD", Modifier.weight(1f))
                    Spacer(Modifier.width(8.dp))
                    DobField(dobYear, { if(it.length <= 4) dobYear = it }, "YYYY", Modifier.weight(1.5f))
                }

                RegistrationTextField(value = password, onValueChange = { password = it }, placeholder = "Password", isPassword = true)

                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = if (agreeToTerms) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF1E88E5),
                        modifier = Modifier.size(24.dp).clickable { agreeToTerms = !agreeToTerms }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Agree with Terms & Conditions",
                        color = Color(0xFF1E88E5),
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = {
                        if (agreeToTerms) {
                            authViewModel.signUp(email, password)
                        } else {
                            Toast.makeText(context, "Please agree to terms", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
                ) {
                    Text("Create account", fontWeight = FontWeight.Bold)
                }
                
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun RegistrationTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        placeholder = { Text(placeholder, color = Color.White, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
        shape = RoundedCornerShape(28.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF42A5F5),
            unfocusedContainerColor = Color(0xFF42A5F5),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        singleLine = true,
        textStyle = TextStyle(textAlign = TextAlign.Center),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun GenderButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF0D47A1) else Color(0xFF42A5F5)
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.height(36.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 0.dp)
    ) {
        Text(text, color = Color.White, fontSize = 14.sp)
    }
}

@Composable
fun DobField(value: String, onValueChange: (String) -> Unit, placeholder: String, modifier: Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.height(40.dp),
        placeholder = { Text(placeholder, color = Color.White, fontSize = 12.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF42A5F5),
            unfocusedContainerColor = Color(0xFF42A5F5),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        singleLine = true,
        textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 12.sp)
    )
}
