package com.example.petsall.ui.components.skeleton

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.petsall.ui.theme.White

@Composable
fun NewShimmerSpacer(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
){
    val colors = listOf(
        Color(0xFFEAEAEA),
        Color(0xFFF6F6F6),
        Color(0xFFEAEAEA)
    )


    val transition = rememberInfiniteTransition()

    val shimmerWidthPercentage = 0.3f

    BoxWithConstraints {
        val spaceMaxWidth = with(LocalDensity.current) { maxWidth.toPx() }
        val spaceMaxHeight = with(LocalDensity.current) { maxHeight.toPx() }

        val translateAnim = transition.animateFloat(
            initialValue = 0f,
            targetValue = spaceMaxWidth * (1 + shimmerWidthPercentage),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )

        val brush = Brush.linearGradient(
            colors,
            start = Offset(translateAnim.value - (spaceMaxWidth * shimmerWidthPercentage),spaceMaxHeight),
            end = Offset(translateAnim.value,spaceMaxHeight)
        )


        Spacer(
            modifier = modifier
                .background(brush = brush)
        )

    }
}


@Composable
fun TopBarSkeleton() {
    Column(
        modifier = Modifier
            .background(White)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ImageTopSkeleton()
            Spacer(modifier = Modifier.width(10.dp))
            NewShimmerSpacer(
                modifier = Modifier
                    .height(30.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxWidth(fraction = 0.4f)
            )

        }
    }
}

@Composable
fun ImageTopSkeleton() {
    NewShimmerSpacer(
        modifier = Modifier
            .height(50.dp)
            .width(50.dp)
            .clip(shape = RoundedCornerShape(50.dp))
    )
}


@Composable
@Preview(showBackground = true)
fun ShimmerPreview() {
    Column(Modifier.fillMaxSize()) {
        TopBarSkeleton()
    }
}