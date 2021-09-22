package com.hdesrosiers.animated3ddropdown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hdesrosiers.animated3ddropdown.ui.theme.Animated3DDropDownTheme

// https://www.youtube.com/watch?v=WdQUDHOwlgE&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=16

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .background(color = Color(0xff101010))
      ) {
        DropDown(text = "Dropdown") {
          Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = Color.Green)
          )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .background(color = Color.Red)
        )
      }
    }
  }
}

@Composable
fun DropDown(
  text: String,
  modifier: Modifier = Modifier,
  initiallyOpened: Boolean = false,
  content: @Composable () -> Unit
) {
  var isOpen by remember { mutableStateOf(initiallyOpened) }
  var alpha = animateFloatAsState(
    targetValue = if (isOpen) 1f else 0f,
    animationSpec = tween(
      durationMillis = 300,
      easing = FastOutSlowInEasing
    )
  )
  var rotateX = animateFloatAsState(
    targetValue = if (isOpen) 0f else -90f,
    animationSpec = tween(
      durationMillis = 300,
      easing = FastOutSlowInEasing
    )
  )
  Column(modifier = modifier.fillMaxWidth()) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = text,
        color = Color.White,
        fontSize = 16.sp,
        modifier = Modifier.padding(16.dp)
      )
      Icon(
        imageVector = Icons.Default.ArrowDropDown,
        contentDescription = "Open or close dropdown",
        tint = Color.White,
        modifier = Modifier
          .size(24.dp)
          .clickable {
            isOpen = !isOpen
          }
          .scale(
            scaleX = 1f,
            scaleY = if (isOpen) -1f else 1f
          )
      )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Box(
      contentAlignment = Alignment.Center,
      modifier = modifier
        .fillMaxWidth()
        .graphicsLayer {
          transformOrigin = TransformOrigin(
            pivotFractionX = 0.5f,
            pivotFractionY = 0f
          )
          rotationX = rotateX.value
        }
        .alpha(alpha = alpha.value)
    ) {
      content()
    }
  }
}

































