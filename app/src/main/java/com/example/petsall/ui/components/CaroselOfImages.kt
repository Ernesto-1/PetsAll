package com.example.petsall.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.petsall.ui.theme.GreenLight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselOfImages(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = 5000,
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val pagerState = rememberPagerState { itemsCount }
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    var key by remember { mutableStateOf(false) }

    LaunchedEffect(key) {
        val target = if (pagerState.currentPage < itemsCount - 1) pagerState.currentPage + 1 else 0
        delay(autoSlideDuration)
        pagerState.animateScrollToPage((target))
        key = !key
    }

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            itemContent(page)
        }

        Surface(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            color = Color.Black.copy(alpha = 0.5f)
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
            )
        }
    }
}


@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = GreenLight,
    unSelectedColor: Color = Color.Gray,
    dotSize: Dp
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        for (index in 0 until totalDots) {
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier, size: Dp, color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}