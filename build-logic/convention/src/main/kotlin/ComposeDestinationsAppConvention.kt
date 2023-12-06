import com.android.build.gradle.AppExtension
import com.google.devtools.ksp.gradle.KspExtension
import dev.weazyexe.fonto.ComposeDestinationsExtensions
import dev.weazyexe.fonto.id
import dev.weazyexe.fonto.libs
import dev.weazyexe.fonto.versioncatalog.composeDestinationsBundle
import dev.weazyexe.fonto.versioncatalog.composeDestinationsKsp
import dev.weazyexe.fonto.versioncatalog.kotlinKsp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class ComposeDestinationsAppConvention : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.kotlinKsp.id)

            val composeDestinations =
                extensions.create<ComposeDestinationsExtensions>("composeDestinations")

            extensions.configure<AppExtension> {
                applicationVariants.all {
                    kotlinExtension.sourceSets.getByName(name) {
                        kotlin.srcDir("build/generated/ksp/$name/kotlin")
                    }
                }
            }

            afterEvaluate {
                extensions.configure<KspExtension> {
                    arg("compose-destinations.mode", composeDestinations.mode.get().origin)
                    arg("compose-destinations.moduleName", composeDestinations.moduleName.get())
                }
            }

            dependencies {
                add("implementation", libs.composeDestinationsBundle)
                add("ksp", libs.composeDestinationsKsp)
            }
        }
    }
}
