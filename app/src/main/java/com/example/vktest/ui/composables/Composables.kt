package com.example.vktest.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.vktest.data.remote.response.product.Product
import com.example.vktest.ui.theme.Purple40
import com.example.vktest.ui.theme.PurpleGrey40

@Composable
fun DropdownCategoriesMenu(
    categories: List<String>,
    expanded: MutableState<Boolean>,
    onPickedCategory: (String) -> Unit
) {
    DropdownMenu(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        onDismissRequest = {
            expanded.value = false
        },
        expanded = expanded.value
    ) {
        categories.forEach { category ->
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                text = { Text(text = category, color = PurpleGrey40) },
                onClick = {
                    onPickedCategory(category)
                    expanded.value = false
                })
        }
    }
}


@Composable
fun ProductItemView(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = "product image",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.DarkGray, CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = product.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 1
                )
                Text(
                    text = product.description,
                    fontSize = 12.sp,
                    lineHeight = TextUnit(16f, TextUnitType.Sp),
                    color = Color.Gray
                )

                Text(
                    text = product.category,
                    modifier = Modifier
                        .border(2.dp, Purple40, RoundedCornerShape(8.dp))
                        .align(Alignment.End)
                        .padding(4.dp),
                    fontSize = 12.sp,
                    color = Color.Black,
                    maxLines = 1
                )
            }
        }
    }
}
