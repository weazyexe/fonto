import com.android.build.gradle.TestExtension
import dev.weazyexe.fonto.configureGradleManagedDevices
import dev.weazyexe.fonto.configureKotlinAndroid
import dev.weazyexe.fonto.id
import dev.weazyexe.fonto.libs
import dev.weazyexe.fonto.versioncatalog.androidTest
import dev.weazyexe.fonto.versioncatalog.kotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidTestConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.androidTest.id)
                apply(libs.kotlinAndroid.id)
            }

            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)
                configureGradleManagedDevices(this)
            }
        }
    }

}
