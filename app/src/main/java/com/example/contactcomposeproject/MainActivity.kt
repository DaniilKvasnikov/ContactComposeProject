package com.example.contactcomposeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Contact(
    val name: String,
    val surname: String? = null,
    val familyName: String,
    val imageRes: Int? = null,
    val isFavorite: Boolean = false,
    val phone: String,
    val address: String,
    val email: String? = null,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ContactDetails(
                    contact = Contact(
                        name = "Евгений",
                        surname = "Андреевич",
                        familyName = "Лукашин",
                        isFavorite = true,
                        phone = "+7 495 495 95 95",
                        address = "г. Москва, 3-я улица Строителей, д. 25, кв. 12",
                        email = "ELukashin@practicum.ru"
                    )
                )
            }
        }
    }
}

@Composable
fun ContactAvatar(contact: Contact) {
    if (contact.imageRes != null) {
        Image(
            painter = painterResource(id = contact.imageRes),
            contentDescription = "Фото контакта",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
    } else {
        val initials = contact.name.take(1) + contact.familyName.take(1)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(80.dp)
                .background(color = Color.Gray, shape = CircleShape)
        ) {
            Text(
                text = initials,
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp)
    ) {
        Text(
            text = stringResource(R.string.info_label, label),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            fontStyle = FontStyle.Italic,
            color = Color.DarkGray,
            fontSize = 16.sp
        )
        Text(
            text = value,
            modifier = Modifier.weight(1.5f),
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetails(contact: Contact) {
    val fullName = remember(contact) {
        buildString {
            append(contact.name)
            if (contact.surname != null) append(" ${contact.surname}")
            append("\n${contact.familyName}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name), color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ContactAvatar(contact = contact)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = fullName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                if (contact.isFavorite) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        painter = painterResource(id = android.R.drawable.star_big_on),
                        contentDescription = "Избранное",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            InfoRow(label = stringResource(R.string.phone), value = contact.phone)

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(label = stringResource(R.string.address), value = contact.address)

            if (contact.email != null) {
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(label = stringResource(R.string.email), value = contact.email)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileWithoutPhotoPreview() {
    ContactDetails(
        contact = Contact(
            name = "Евгений",
            surname = "Андреевич",
            familyName = "Лукашин",
            isFavorite = true,
            phone = "+7 495 495 95 95",
            address = "г. Москва, 3-я улица Строителей, д. 25, кв. 12",
            email = "ELukashin@practicum.ru"
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileWithPhotoPreview() {
    ContactDetails(
        contact = Contact(
            name = "Василий",
            familyName = "Кузякин",
            imageRes = R.drawable.ic_user_icon,
            isFavorite = false,
            phone = "---",
            address = "Ивановская область, дер. Крутово, д. 4"
        )
    )
}
