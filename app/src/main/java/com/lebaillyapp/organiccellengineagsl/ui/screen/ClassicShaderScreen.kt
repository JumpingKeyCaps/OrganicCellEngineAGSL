package com.lebaillyapp.organiccellengineagsl.ui.screen

import androidx.annotation.RawRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.lebaillyapp.organiccellengineagsl.ui.composition.OrganicShaderClassic
import androidx.compose.ui.platform.LocalResources

/**
 * # ClassicShaderScreen
 * Screen demonstrating the non-interactive version of the organic shader.
 * Transitions between states (Idle, Loading, Error) via click.
 */
@Composable
fun ClassicShaderScreen(@RawRes shaderSource: Int) {
    val contextRes = LocalResources.current

    // Chargement de la source depuis res/raw
    val shaderSource = remember {
        contextRes.openRawResource(shaderSource).bufferedReader().use { it.readText() }
    }

    var targetProgress by remember { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(1000),
        label = "ShaderProgress"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(interactionSource = remember { MutableInteractionSource() },indication = null) {
                targetProgress = when (targetProgress) {
                    0f -> 0.5f
                    0.5f -> 1f
                    else -> 0f
                }

            }
    ) {
        OrganicShaderClassic(
            shaderSource = shaderSource,
            progress = animatedProgress
        )
    }
}