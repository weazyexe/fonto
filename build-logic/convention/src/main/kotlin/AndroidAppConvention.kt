import com.android.build.api.dsl.ApplicationExtension
import dev.weazyexe.fonto.configureKotlinAndroid
import dev.weazyexe.fonto.id
import dev.weazyexe.fonto.libs
import dev.weazyexe.fonto.versioncatalog.androidApplication
import dev.weazyexe.fonto.versioncatalog.applicationId
import dev.weazyexe.fonto.versioncatalog.kotlinAndroid
import dev.weazyexe.fonto.versioncatalog.kotlinKsp
import dev.weazyexe.fonto.versioncatalog.kotlinParcelize
import dev.weazyexe.fonto.versioncatalog.kotlinSerialization
import dev.weazyexe.fonto.versioncatalog.targetSdk
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidAppConvention : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.androidApplication.id)
                apply(libs.kotlinAndroid.id)
                apply(libs.kotlinKsp.id)
                apply(libs.kotlinSerialization.id)
                apply(libs.kotlinParcelize.id)
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    targetSdk = libs.targetSdk
                    applicationId = libs.applicationId
                    versionCode = 1
                    versionName = "1.0"

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }
        }
    }

}
