package com.example.presenter.components.brush

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SkeletonListLoader(
    modifier: Modifier = Modifier,
    times: Int,
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 36.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        repeat(times) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(shimmerBrush())
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SkeletonListLoaderPreview() {
    SkeletonListLoader(times = 10)
}
