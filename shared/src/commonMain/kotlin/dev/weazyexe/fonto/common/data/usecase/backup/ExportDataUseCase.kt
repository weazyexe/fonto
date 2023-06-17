package dev.weazyexe.fonto.common.data.usecase.backup

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.feature.backup.FileSaver
import dev.weazyexe.fonto.common.model.backup.ExportStrategy
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

internal class ExportDataUseCase(
    private val getExportData: GetExportDataUseCase
) {

    operator fun invoke(
        strategy: ExportStrategy,
        saver: FileSaver
    ): Flow<AsyncResult<Unit>> = flowIo {
        emit(AsyncResult.Loading())
        val data = getExportData(strategy)
        saver.save(data.toByteArray())
        emit(AsyncResult.Success(Unit))
    }
}