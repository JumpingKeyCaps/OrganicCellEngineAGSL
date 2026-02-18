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
import androidx.compose.ui.platform.LocalContext
import com.lebaillyapp.organiccellengineagsl.ui.composition.OrganicShaderInteractive

/**
 * # InteractiveShaderScreen
 * Screen demonstrating the touch-reactive version of the organic shader.
 * Transitions between states via click, and distorts cells via drag.
 */
@Composable
fun InteractiveShaderScreen(@RawRes shaderRawId: Int) {
    val context = LocalContext.current

    // Chargement de la source AGSL depuis res/raw
    val sourceCode = remember(shaderRawId) {
        context.resources.openRawResource(shaderRawId).bufferedReader().use { it.readText() }
    }

    // Gestion du progress pour les transitions de couleurs
    var targetProgress by remember { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(1000),
        label = "InteractiveShaderProgress"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(interactionSource = remember { MutableInteractionSource() },indication = null) {
                // On garde le cycle d'état sur le simple clic
                targetProgress = when (targetProgress) {
                    0f -> 0.5f
                    0.5f -> 1f
                    else -> 0f
                }
            }
    ) {
        OrganicShaderInteractive(
            shaderSource = sourceCode,
            progress = animatedProgress
        )
    }
}