package com.example.chessinsight.engine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.concurrent.atomic.AtomicBoolean

class StockfishEngine(private val binaryPath: String) {
    private var process: Process? = null
    private var reader: BufferedReader? = null
    private var writer: OutputStreamWriter? = null
    
    private val _engineOutput = MutableStateFlow<String>("")
    val engineOutput: StateFlow<String> = _engineOutput

    private val isRunning = AtomicBoolean(false)

    suspend fun start() = withContext(Dispatchers.IO) {
        if (isRunning.get()) return@withContext
        try {
            val processBuilder = ProcessBuilder(binaryPath)
            process = processBuilder.start()
            reader = BufferedReader(InputStreamReader(process!!.inputStream))
            writer = OutputStreamWriter(process!!.outputStream)
            isRunning.set(true)
            
            // Read output in background
            Thread {
                try {
                    var line: String?
                    while (isRunning.get() && reader?.readLine().also { line = it } != null) {
                        _engineOutput.value = line ?: ""
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
            
            sendCommand("uci")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun sendCommand(command: String) = withContext(Dispatchers.IO) {
        if (!isRunning.get()) return@withContext
        try {
            writer?.write("\$command\\n")
            writer?.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun stop() = withContext(Dispatchers.IO) {
        isRunning.set(false)
        sendCommand("quit")
        try {
            process?.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        process?.destroy()
        reader?.close()
        writer?.close()
    }
}
