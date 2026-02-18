# AGSL Organic Cell Engine: Beyond Procedural Staticity

![Platform](https://img.shields.io/badge/Platform-Android-brightgreen.svg)
![Language](https://img.shields.io/badge/Language-AGSL%20%2F%20Kotlin-blue.svg)
![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4.svg)
![Min-SDK](https://img.shields.io/badge/Min--SDK-33%2B-orange.svg)
![License](https://img.shields.io/badge/License-MIT-lightgrey.svg)

This project showcases a high-performance, interactive background engine for Jetpack Compose, leveraging AGSL (Android Graphics Shading Language). It moves away from classic linear gradients and predictable sine-waves to implement a living, breathing organic environment based on Dynamic Voronoi Tesselation.

--

| Classic Organic Cell Shader | Alt Organic Cell Shader | Advanced Organic Cell layers Shader | 
|:---:|:---:|:---:|
| ![P1](screenshots/classic.gif) | ![P2](screenshots/classic2.gif) |  ![P3](screenshots/advanced.gif) |


---

## The Concept: Living Geometry

### What is Voronoi?
In nature, patterns like dragonfly wings, dried mud, or cellular structures follow a Voronoi diagram logic: a partition of space into regions based on distance to points in a specific subset of the plane. 

In this engine, we don't just draw cells; we animate the "seeds" (the nuclei of the cells) using asynchronous trigonometric phases. The result is a non-cyclical topological shift where boundaries merge and split like biological membranes.

### The Deep-Layer Architecture
To achieve a Premium feel, the engine doesn't rely on a single calculation. It overlays two distinct Voronoi octaves:
* The Macro Layer: Handles the primary color masses and slow fluid movement.
* The Detail Layer: A high-frequency, counter-animated layer that creates internal parallax, giving an illusion of 3D depth within a 2D plane.

---

## Spatial Awareness & Haptic Interaction

One of the core features of this engine is its Reactive Topology. By passing real-time touch coordinates (uTouch) to the GPU, the shader creates a localized force field.

Instead of a simple overlay, the touch input distorts the underlying space. As your finger moves, the surrounding cell nuclei are repelled or attracted based on a Gaussian falloff function. This simulates surface tension and viscosity, making the UI feel like a physical substance rather than a flat image.

---

## Technical Implementation & GPU Optimization

### Emotion-Driven State Machine
The shader acts as an Emotion Engine driven by a single uProgress uniform. It interpolates between three handcrafted chromatic palettes:
* Idle (Stable): Deep, calm blues.
* Active (Processing): Vibrant, pulsing purples.
* Alert (Error): High-contrast, aggressive reds.

The transition uses a non-linear smoothstep interpolation to ensure that state changes feel like a natural evolution of the matter.

### Performance First
* Zero-Alloc Rendering: All calculations happen per-pixel on the GPU. The CPU only dispatches a few floats per frame.
* Half-Precision Floating Point: Specifically tuned for ARM architectures to maximize battery life while maintaining visual fidelity.
* Aspect-Ratio Correction: Integrated normalization logic ensures that cells remain perfectly organic on any form factor, from Foldables to Tablets.

### The "Black Hole" Challenge: Neighborhood Integrity

A common pitfall in dynamic Voronoi implementations is the **Neighborhood Rupture**, often manifesting as "Black Holes" (dead pixels) during intense interaction.

**The Problem:** Standard Voronoi shaders optimize performance by checking only the 9 immediate neighbor cells (3x3 grid) for each pixel. 

When a strong interaction force (`REPEL_STRENGTH`) is applied, a cell nucleus can be pushed so far that it exits the search perimeter of the surrounding pixels.
The pixel then loses its "closest seed" reference, resulting in a mathematical void—a black spot on the screen where the distance calculation fails.

**The Solution:** To maintain topological integrity even under extreme distortion, this engine implements an **Extended 5x5 Search Grid**:

* **Expanded Horizon:** By calculating 25 neighboring points instead of 9, the engine keeps track of "displaced" nuclei that have been propelled by the user's touch.
* **Vector Repulsion:** We use a repulsion vector that displaces the cell's seed within the grid, ensuring that while the cell's *shape* is deformed, its *identity* remains within a detectable range for the GPU.
* **Glitch-Free Fluidity:** This architectural choice ensures a "bulletproof" interaction, allowing for high `CELL_DENSITY` or wide `TOUCH_RADIUS` without ever breaking the visual continuity of the organic matter.

---

## Configuration

The engine is built with a Developer-First mindset. A configuration block at the top of the shader allows for instant tweaking of:
* Cell Density: Scale the world from microscopic to macroscopic.
* Fluid Velocity: Adjust the perceived viscosity of the liquid.
* Interaction Radius: Define how far the touch influence ripples through the cells.

---

**Built with AGSL | Powered by Jetpack Compose**
