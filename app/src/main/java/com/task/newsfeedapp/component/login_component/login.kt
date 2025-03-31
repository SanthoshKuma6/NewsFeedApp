package com.task.newsfeedapp.component.login_component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CountryCodeTextField() {
    var phoneNumber by remember { mutableStateOf("") }
    var selectedCountryCode by remember { mutableStateOf("+1") }
    var expanded by remember { mutableStateOf(false) }

    val countryCodes = listOf("+1", "+91", "+44", "+61") // Add more codes as needed
    val fieldShape = RoundedCornerShape(12.dp) // Common shape for both elements

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Country Code Button
        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .height(56.dp), // Match OutlinedTextField height
                shape = fieldShape, // Apply same shape
                border = BorderStroke(1.dp, Color.Gray) ,
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White) // Set inside color to white


            ) {
                Text(selectedCountryCode)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                countryCodes.forEach { code ->
                    DropdownMenuItem(
                        text = { Text(code) },
                        onClick = {
                            selectedCountryCode = code
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(Modifier.width(10.dp))

        // Phone Number Field
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            placeholder = { Text("Enter Mobile Number") }, // Placeholder instead of label
            modifier = Modifier
                .weight(1f)
                .height(56.dp), // Ensures same height
            shape = fieldShape, // Apply same shape
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            singleLine = true, // Prevents vertical expansion
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White, // Background when focused
                unfocusedContainerColor = Color.White, // Background when not focused
                focusedBorderColor = Color.Blue, // Border color when focused
                unfocusedBorderColor = Color.Gray, // Border color when not focused
                cursorColor = Color.Black, // Cursor color
                focusedTextColor = Color.Black, // Text color inside the field
                unfocusedTextColor = Color.Black
            )
        )
    }
}




@Composable
fun TermsAndPrivacyText(onTermsClicked: () -> Unit, onPrivacyClicked: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        append("By signing up, you agree to our ")

        pushStringAnnotation(tag = "TERMS", annotation = "terms")
        withStyle(style = SpanStyle(color = Color.Yellow)) { append("Terms of Use") }
        pop()

        append(" and ")

        pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
        withStyle(style = SpanStyle(color = Color.Yellow)) { append("Privacy Policy") }
        pop()
    }

    Text(
        text = annotatedText,
        fontSize = 12.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle Clicks Here */ }
    )
}

@Composable
fun PrivacyAndTerms() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Other UI elements...

        Spacer(modifier = Modifier.height(10.dp))

        TermsAndPrivacyText(
            onTermsClicked = { /* Navigate to Terms */ },
            onPrivacyClicked = { /* Navigate to Privacy */ }
        )
    }
}


@Composable
fun OtpInputBox(otpValue: String, onOtpChange: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    val focusRequesters = List(4) { FocusRequester() }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in 0 until 4) {
            TextField(
                value = otpValue.getOrNull(i)?.toString() ?: "",
                onValueChange = { newValue ->
                    if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                        val newOtp = otpValue.toMutableList()
                        if (newValue.isNotEmpty()) {
                            if (newOtp.size > i) newOtp[i] = newValue[0] else newOtp.add(newValue[0])
                            if (i < 3) focusRequesters[i + 1].requestFocus()
                        } else {
                            if (newOtp.size > i) newOtp.removeAt(i)
                            if (i > 0) focusRequesters[i - 1].requestFocus()
                        }
                        onOtpChange(newOtp.joinToString(""))
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                modifier = Modifier
                    .size(70.dp)
                    .focusRequester(focusRequesters[i])
                    .padding(8.dp), shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White, // Background when focused
                    unfocusedContainerColor = Color.White, // Background when not focused
                    focusedBorderColor = Color.Blue, // Border color when focused
                    unfocusedBorderColor = Color.Gray, // Border color when not focused
                    cursorColor = Color.Black, // Cursor color
                    focusedTextColor = Color.Black, // Text color inside the field
                    unfocusedTextColor = Color.Black
                )
            )
        }
    }
}
