package com.roland.android.palette.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun DrawingBoard() {
	Scaffold { paddingValues ->
		val paths = remember { mutableStateListOf<PathProperties>() }
		var drawMode by remember { mutableStateOf(DrawMode.Draw) }
		var scale by remember { mutableFloatStateOf(1f) }
		var offset by remember { mutableStateOf(Offset.Zero) }

		BoxWithConstraints(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues),
			contentAlignment = Alignment.BottomCenter
		) {
			val state = rememberTransformableState { zoomChange, panChange, rotationChange ->
				scale = (scale * zoomChange).coerceIn(1f, 5f)

				val extraWidth = (scale - 1) * constraints.maxWidth
				val extraHeight = (scale - 1) * constraints.maxHeight

				val maxX = extraWidth / 2
				val maxY = extraHeight / 2

				offset = Offset(
					x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
					y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY)
				)
			}

			Canvas(
				modifier = Modifier
					.fillMaxSize()
					.background(Color.White)
					.graphicsLayer {
						scaleX = scale
						scaleY = scale
						translationX = offset.x
						translationY = offset.y
					}
					.transformable(state)
					.pointerInput(true) {
						if (drawMode == DrawMode.Touch) return@pointerInput
						detectDragGestures { change, dragAmount ->
							change.consume()
							val eraseMode = drawMode == DrawMode.Erase
							val path = PathProperties(
								color = when (drawMode) {
									DrawMode.Erase -> Color.White
									DrawMode.Draw -> Color.Black
									else -> Color.Transparent
								},
								eraseMode = eraseMode,
								start = change.position - dragAmount,
								end = change.position,
							)

							paths.add(path)
						}
					}
			) {
				paths.forEach { path ->
					drawLine(
						color = path.color,
						start = path.start,
						end = path.end,
						strokeWidth = path.strokeWidth,
						cap = path.strokeCap
					)
				}
			}

			PaletteMenu(
				drawMode = drawMode,
				onDrawModeChanged = { drawMode = it }
			)
		}
	}
}