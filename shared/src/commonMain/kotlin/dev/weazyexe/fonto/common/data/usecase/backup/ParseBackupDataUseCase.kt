package dev.weazyexe.fonto.common.data.usecase.backup

import dev.weazyexe.fonto.common.feature.backup.FileReader
import dev.weazyexe.fonto.common.model.backup.FontoBackupModel
import kotlinx.serialization.json.Json

internal class ParseBackupDataUseCase(
    private val json: Json
) {

    suspend operator fun invoke(
        fileReader: FileReader
    ): FontoBackupModel {
        val backupInJsonFormat = fileReader.read().decodeToString()
        return json.decodeFromString(backupInJsonFormat)
    }
}