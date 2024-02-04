package com.example.draggable_music_knob

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.atan2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DraggableMusicKnob()
        }
    }
}

@Composable
fun DraggableMusicKnob(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row {
            Draggable()
            Spacer(modifier = Modifier.width(20.dp))
            VolumeBar()
        }
    }
}

@Composable
fun VolumeBar(){
    Text(text = "100%")
}

@Composable
fun Draggable(
    modifier:Modifier = Modifier,
    limitingAngle:Float = 25f,
    onValueChange:(Float)->Unit
){
    var rotation by remember {
        mutableFloatStateOf(limitingAngle)
    }
    var touchX by remember {
        mutableFloatStateOf(0f)
    }
    var touchY by remember {
        mutableFloatStateOf(0f)
    }
    var centerX by remember {
        mutableFloatStateOf(0f)
    }
    var centerY by remember {
        mutableFloatStateOf(0f)
    }
    //Text(text = "on rotate is 180")
    Image(
        painter = painterResource(id = R.drawable.music_knob),
        contentDescription = "draggable music knob",
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                val windowBounds = it.boundsInWindow()
                centerX = windowBounds.size.width / 2f
                centerY = windowBounds.size.height / 2f

            }
            .pointerInteropFilter {event ->
                touchX = event.x
                touchY = event.y
                val angle = -atan2(centerX - touchX, centerY - touchY)
            }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DraggableMusicKnob()
}