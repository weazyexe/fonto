package dev.weazyexe.fonto.baselineprofile

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import org.junit.Rule
import org.junit.Test

class Benchmark {

    @get:Rule
    val macrobenchmarkRule = MacrobenchmarkRule()

    @Test
    fun startupNoBaselineProfiles() = startup(CompilationMode.None())

    @Test
    fun startupWithBaselineProfiles() = startup(CompilationMode.Partial())

    private fun startup(compilationMode: CompilationMode) = macrobenchmarkRule.measureRepeated(
        packageName = "dev.weazyexe.fonto.benchmark",
        metrics = listOf(
            StartupTimingMetric(),
            FrameTimingMetric()
        ),
        compilationMode = compilationMode,
        iterations = 5,
        startupMode = StartupMode.COLD,
        measureBlock = {
            startupAndScrollFeed()
        }
    )
}