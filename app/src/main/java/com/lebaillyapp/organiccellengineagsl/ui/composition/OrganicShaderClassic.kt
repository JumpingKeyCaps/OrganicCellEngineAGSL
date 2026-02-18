package com.lebaillyapp.organiccellengineagsl.ui.composition

import android.graphics.RuntimeShader
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.ShaderBrush

/**
 * # OrganicShaderClassic
 * A composable that renders a hardware-accelerated AGSL effect.
 *
 * This component handles the lifecycle of a [RuntimeShader], providing high-performance
 * frame-perfect synchronization for fluid, organic animations.
 *
 * @param shaderSource The AGSL source code to be compiled. Re-compilation occurs
 * only if this string changes.
 * @param progress A normalized value [0..1] typically used to interpolate between
 * different visual states (e.g., Idle, Active, Error).
 * @param modifier Modifier used to adjust the layout or behavior of the shader container.
 *
 * Technical Details:
 * - Time Management: Uses [withFrameNanos] to ensure time increments are synced
 * with the display refresh rate (VSync), preventing micro-stuttering.
 * - Drawing Strategy: Implements [drawWithCache] to decouple uniform updates
 * from the creation of the [ShaderBrush], minimizing object allocation during the draw phase.
 * - GPU Uniforms: Automatically injects:
 * - uTime (float): Elapsed time in seconds.
 * - uProgress (float): Current state interpolation value.
 * - iResolution (float2): Dimensions of the drawing area.
 */
@Composable
fun OrganicShaderClassic(
    shaderSource: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    // Keeps the compiled shader in memory across recompositions.
    val shader = remember(shaderSource) { RuntimeShader(shaderSource) }

    // Synchronized clock for GPU-side time-based animations.
    var time by remember { mutableFloatStateOf(0f) }
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
            .drawWithCache {
                // Efficiently dispatching uniforms to the GPU.
                shader.setFloatUniform("uTime", time)
                shader.setFloatUniform("uProgress", progress)
                shader.setFloatUniform("iResolution", size.width, size.height)

                val brush = ShaderBrush(shader)

                onDrawBehind {
                    drawRect(brush)
                }
            }
    )
}