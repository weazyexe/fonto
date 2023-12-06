import com.android.build.gradle.LibraryExtension
import dev.weazyexe.fonto.configureKotlinAndroid
import dev.weazyexe.fonto.id
import dev.weazyexe.fonto.libs
import dev.weazyexe.fonto.versioncatalog.androidLibrary
import dev.weazyexe.fonto.versioncatalog.kotlinAndroid
import dev.weazyexe.fonto.versioncatalog.kotlinKsp
import dev.weazyexe.fonto.versioncatalog.kotlinParcelize
import dev.weazyexe.fonto.versioncatalog.kotlinSerialization
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConvention : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.androidLibrary.id)
                apply(libs.kotlinAndroid.id)
                apply(libs.kotlinKsp.id)
                apply(libs.kotlinSerialization.id)
                apply(libs.kotlinParcelize.id)
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }
            }
        }
    }
}
