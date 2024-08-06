package com.roland.android.palette.ui.screens

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin

data class PathProperties(
	var alpha: Float = 1f,
	var color: Color = Color.Black,
	var eraseMode: Boolean = false,
	val start: Offset = Offset.Zero,
	val end: Offset = Offset.Zero,
	var strokeCap: StrokeCap = StrokeCap.Round,
	var strokeJoin: StrokeJoin = StrokeJoin.Round,
	var strokeWidth: Float = 10f
)
