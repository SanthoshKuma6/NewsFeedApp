package com.task.newsfeedapp.screens

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun ChangeBackgroundScreen(navController: NavController) {
    val context = LocalContext.current
    var backgroundImageUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(true) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            backgroundImageUri = it
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = saveImageToGallery(context, it)
            backgroundImageUri = uri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        backgroundImageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it, contentScale = ContentScale.Crop),
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onBackground)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showDialog = true },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Change Background")
        }

        if (showDialog) {
            ShowImagePickerDialog(
                onDismiss = { showDialog = false },
                onPickGallery = { galleryLauncher.launch("image/*") },
                onCapturePhoto = { cameraLauncher.launch(null) }
            )
        }
    }
}
//
//@Composable
//fun ShowImagePickerDialog(
//    onDismiss: () -> Unit,
//    onPickGallery: () -> Unit,
//    onCapturePhoto: () -> Unit
//) {
//    AlertDialog(
//        onDismissRequest = { onDismiss() },
//        title = { Text("Select Image Source") },
//        text = {
//            Column {
//                Button(onClick = {
//                    onPickGallery()
//                    onDismiss()
//                }) {
//                    Text("Choose from Gallery")
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Button(onClick = {
//                    onCapturePhoto()
//                    onDismiss()
//                }) {
//                    Text("Take a Photo")
//                }
//            }
//        },
//        confirmButton = {},
//        dismissButton = {
//            Button(onClick = { onDismiss() }) {
//                Text("Cancel")
//            }
//        }
//    )
//}
//
//fun saveImageToGallery(context: Context, bitmap: Bitmap): Uri? {
//    val contentValues = ContentValues().apply {
//        put(MediaStore.Images.Media.DISPLAY_NAME, "Captured_Image_${System.currentTimeMillis()}.jpg")
//        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
//        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraApp")
//        put(MediaStore.Images.Media.IS_PENDING, 1)
//    }
//
//    val contentResolver = context.contentResolver
//    val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//
//    uri?.let { outputStream ->
//        contentResolver.openOutputStream(outputStream)?.use { outStream ->
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
//        }
//        contentValues.clear()
//        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
//        contentResolver.update(uri, contentValues, null, null)
//    }
//
//    return uri
//}
