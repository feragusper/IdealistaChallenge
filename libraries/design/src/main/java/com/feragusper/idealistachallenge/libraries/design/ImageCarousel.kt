package com.feragusper.idealistachallenge.libraries.design

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

/**
 * Composable function for displaying an image carousel.
 *
 * @param images A list of image URLs to be displayed in the carousel.
 */
@Composable
private fun ImageCarousel(images: List<String>) {
    if (images.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { images.size })

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            Image(
                painter = rememberAsyncImagePainter(images[page]),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = "${pagerState.currentPage + 1}/${images.size}",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Custom view for displaying an image carousel.
 *
 * @param context The context of the view.
 * @param attrs The attributes of the view.
 * @param defStyleAttr The default style attribute of the view.
 */
class ImageCarouselView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    private var _images by mutableStateOf(emptyList<String>())
    var images: List<String>
        get() = _images
        set(value) {
            _images = value
        }

    /**
     * Composable content for the view.
     */
    @Composable
    override fun Content() {
        ImageCarousel(images = _images)
    }
}

@Preview
@Composable
internal fun PreviewImageCarousel() {
    ImageCarousel(
        listOf(
            "https://via.placeholder.com/150",
            "https://via.placeholder.com/200",
            "https://via.placeholder.com/250"
        )
    )
}

