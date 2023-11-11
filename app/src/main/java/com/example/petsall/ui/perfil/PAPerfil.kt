package com.example.petsall.ui.perfil

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsall.R
import com.firebase.ui.auth.AuthUI


@Composable
fun PAProfile(
    logout: () -> Unit
) {
    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences("nombre_pref", Context.MODE_PRIVATE)
    val nickName = sharedPreferences.getString("nickName", "")

    Column(
        modifier = Modifier.padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(
            modifier = Modifier
                .height(30.dp)
                .background(Color.Transparent)
        )
        Image(
            painter = painterResource(id = R.drawable.logo_svg_sinbackground),
            contentDescription = "logo",
            modifier = Modifier.height(240.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = "Hola $nickName!",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(id = R.string.profile_coming_soon),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
            color = Color(0x80963CAC),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 35.dp),
            textAlign = TextAlign.Center
        )

        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 8.dp))
                .padding(top = 70.dp)
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .padding(0.5.dp)
                .width(209.dp)
                .height(34.dp)
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .clickable {
                    AuthUI
                        .getInstance()
                        .signOut(context)
                        .addOnCompleteListener {
                            logout.invoke()
                        }
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.profile_logout),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0x80000000),
                    )
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_logout),
                    contentDescription = "logout"
                )
            }
        }
    }
}