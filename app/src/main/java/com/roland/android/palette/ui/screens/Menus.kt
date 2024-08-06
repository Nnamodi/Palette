package com.roland.android.palette.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Brush
import androidx.compose.material.icons.rounded.TouchApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.roland.android.palette.R

@Composable
fun PaletteMenu(
	modifier: Modifier = Modifier,
	drawMode: DrawMode,
	onDrawModeChanged: (DrawMode) -> Unit
) {
	var currentDrawMode = drawMode

	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(16.dp)
			.clip(MaterialTheme.shapes.large)
			.background(Color.LightGray)
			.padding(8.dp),
		horizontalArrangement = Arrangement.SpaceAround,
		verticalAlignment = Alignment.CenterVertically
	) {
		IconButton(
			onClick = {
				currentDrawMode = if (currentDrawMode == DrawMode.Touch) DrawMode.Draw else DrawMode.Touch
				onDrawModeChanged(currentDrawMode)
			}
		) {
			Icon(
				imageVector = Icons.Rounded.TouchApp,
				contentDescription = "Touch mode",
				tint = if (currentDrawMode == DrawMode.Touch) Color.Black else Color.White
			)
		}
		IconButton(
			onClick = {
				if (currentDrawMode == DrawMode.Draw) return@IconButton
				currentDrawMode = DrawMode.Draw
				onDrawModeChanged(currentDrawMode)
			}
		) {
			Icon(
				imageVector = Icons.Rounded.Brush,
				contentDescription = "Drawing mode",
				tint = if (currentDrawMode == DrawMode.Draw) Color.Black else Color.White
			)
		}
		IconButton(
			onClick = {
				currentDrawMode = if (currentDrawMode == DrawMode.Erase) DrawMode.Draw else DrawMode.Erase
				onDrawModeChanged(currentDrawMode)
			}
		) {
			Icon(
				painter = painterResource(R.drawable.eraser_icon),
				contentDescription = "Erase mode",
				modifier = Modifier.scale(0.5f),
				tint = if (currentDrawMode == DrawMode.Erase) Color.Black else Color.White
			)
		}
	}
}

enum class DrawMode {
	Draw, Erase, Touch
}
