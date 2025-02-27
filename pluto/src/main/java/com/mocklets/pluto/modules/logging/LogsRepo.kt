package com.mocklets.pluto.modules.logging

import androidx.lifecycle.MutableLiveData

internal object LogsRepo {

    private val logs = MutableLiveData<List<LogData>>()
    private val logsList = arrayListOf<LogData>()
    private const val MAX_LIMIT = 256

    fun save(level: Level, tag: String, message: String?, tr: Throwable?, ele: StackTraceElement) {
        logsList.add(0, LogData(level, tag, message ?: "", tr, ele))
        val temp = logsList.take(MAX_LIMIT)
        logsList.clear()
        logsList.addAll(temp)
        logs.postValue(logsList)
    }

    fun saveEvent(
        level: Level,
        tag: String,
        event: String?,
        attributes: HashMap<String, Any?>?,
        ele: StackTraceElement
    ) {
        logsList.add(0, LogData(level, tag, event ?: "", null, ele, attributes))
        val temp = logsList.take(MAX_LIMIT)
        logsList.clear()
        logsList.addAll(temp)
        logs.postValue(logsList)
    }

    fun getLogsLD() = logs

    fun deleteAll() {
        logsList.clear()
        logs.postValue(logsList)
    }
}
