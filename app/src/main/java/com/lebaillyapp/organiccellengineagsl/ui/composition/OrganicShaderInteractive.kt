package com.lebaillyapp.organiccellengineagsl.ui.composition

import android.graphics.RuntimeShader
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.input.pointer.pointerInput

/**
 * # OrganicShaderInteractive
 * A composable that renders a hardware-accelerated AGSL effect with touch support.
 * * Unlike the classic version, this component captures touch coordinates and
 * dispatches them to the GPU via the 'uTouch' uniform.
 *
 * @param shaderSource The AGSL source code.
 * @param progress Normalized value [0..1] for state transitions.
 * @param modifier Base modifier.
 */
@Composable
fun OrganicShaderInteractive(
    shaderSource: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val shader = remember(shaderSource) { RuntimeShader(shaderSource) }
    var time by remember { mutableStateOf(0f) }

    // Initialisation du point de contact au centre pour éviter un saut visuel
    var touchPos by remember { mutableStateOf(Offset.Zero) }

    LaunchedEffect(shaderSource) {
        val startTime = System.nanoTime()
        while (true) {
            withFrameNanos { frameTime ->
                time = (frameTime - startTime) / 1_000_000_000f
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            // Captation des mouvements du doigt
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { touchPos = it },
                    onDrag = { change, _ ->
                        touchPos = change.position
                    },
                    onDragEnd = { /* Optionnel : reset ou animation de sortie */ }
                )
            }
            .drawWithCache {
                shader.setFloatUniform("uTime", time)
                shader.setFloatUniform("uProgress", progress)
                shader.setFloatUniform("iResolution", size.width, size.height)

                // On envoie les coordonnées brutes, le shader s'occupera de la normalisation
                shader.setFloatUniform("uTouch", touchPos.x, touchPos.y)

                val brush = ShaderBrush(shader)
                onDrawBehind {
                    drawRect(brush)
                }
            }
    )
}