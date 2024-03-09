package com.example.vktest.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.vktest.R
import com.example.vktest.data.remote.response.product.Product
import com.example.vktest.ui.theme.Purple40
import com.example.vktest.ui.theme.PurpleGrey40

@Composable
fun DropdownCategoriesMenu(
    modifier: Modifier,
    categories: List<String>,
    expanded: MutableState<Boolean>,
    onPickedCategory: (String) -> Unit
) {
    DropdownMenu(
        modifier = modifier,
        onDismissRequest = {
            expanded.value = false
        },
        expanded = expanded.value
    ) {
        categories.forEach { category ->
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                text = {
                    Text(
                        text = category,
                        color = PurpleGrey40
                    )
                },
                onClick = {
                    onPickedCategory(category)
                    expanded.value = false
                })
        }
    }
}

//TODO во все композаблы добавить модифаер, вывод по категории и по поиску


@Composable
fun ProductItemView(product: Product, onNavigate: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .clickable { onNavigate(product) },
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
                    color = Color.Black
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductPager(product: Product) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val state = rememberPagerState(pageCount = { product.images.size })
        HorizontalPager(
            state = state,
            modifier = Modifier
                .size(300.dp)
                .padding(top = 4.dp)
                .clip(RoundedCornerShape(24.dp))
        ) { page ->
            val model = product.images[page]
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = model,
                contentDescription = "Product image",
                contentScale = ContentScale.Crop
            )
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(state.pageCount) { iteration ->
                val color = if (state.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }
    }
}

@Composable
fun ProductInfo(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.White
                        )
                    ) {
                        append(product.price.toString())
                        append("$ ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.White,
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = 12.sp
                        )
                    ) {
                        val noDiscountPrice = String.format(
                            "%.1f",
                            product.price / ((100 - product.discountPercentage) / 100)
                        )
                        append(noDiscountPrice)
                        append("$")
                    }
                },
                modifier = Modifier
                    .background(Purple40, RoundedCornerShape(8.dp))
                    .padding(4.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            StarsRating(rating = product.rating, modifier = Modifier.wrapContentSize())
        }
        Text(
            text = buildString {
                append(product.brand)
                append(" ")
                append(product.title)
            },
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Black
        )
        Text(
            text = product.description,
            fontSize = 18.sp,
            lineHeight = TextUnit(18f, TextUnitType.Sp),
            color = Color.Gray
        )
    }
}

@Composable
fun StarsRating(rating: Double, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            if (rating >= i) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "Rating of product",
                    tint = Purple40
                )
            } else
                Icon(
                    painter = painterResource(id = R.drawable.ic_rounded_halfstar),
                    contentDescription = "Rating of product",
                    tint = Purple40
                )
        }
        Text(text = rating.toString(), color = Purple40, fontSize = 14.sp)
    }
}