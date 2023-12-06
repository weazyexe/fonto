import com.android.build.api.dsl.LibraryExtension
import dev.weazyexe.fonto.configureAndroidCompose
import dev.weazyexe.fonto.id
import dev.weazyexe.fonto.libs
import dev.weazyexe.fonto.versioncatalog.androidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConvention : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.androidLibrary.id)

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }

}
