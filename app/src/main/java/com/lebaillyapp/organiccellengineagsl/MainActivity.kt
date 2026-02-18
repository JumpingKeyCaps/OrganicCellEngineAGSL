package com.lebaillyapp.organiccellengineagsl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import com.lebaillyapp.organiccellengineagsl.ui.screen.ClassicShaderScreen
import com.lebaillyapp.organiccellengineagsl.ui.screen.InteractiveShaderScreen
import com.lebaillyapp.organiccellengineagsl.ui.theme.OrganicCellEngineAGSLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OrganicCellEngineAGSLTheme {
                Box {
                    //todo -  1. Classic shader visualiser (non-interactive)
            //        ClassicShaderScreen(R.raw.organic_cells_shader)
                    //todo -  2. Classic shader with 2 layer visualiser (non-interactive)
            //        ClassicShaderScreen(R.raw.organic_cells_shader_layers)
                    //todo -  3. Interactive shader visualiser (interactive)
            //        InteractiveShaderScreen(shaderRawId = R.raw.deep_cells_touch)
                    //todo -  4. Interactive shader advanced layer (interactive)
                    InteractiveShaderScreen(shaderRawId = R.raw.deep_cells_touch_advanced)

                }
            }
        }
    }
}
