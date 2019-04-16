package com.acomodeo.mining.backend.inputOutput

import com.acomodeo.mining.model.ProcessFlow
import org.springframework.stereotype.Service
import java.io.FileWriter
import java.time.LocalDate

@Service
class Formatting {

    fun writeData(processList: List<ProcessFlow>) {
        val fileWriter = FileWriter("prom_${LocalDate.now()}.csv")
        val csvHEADER = "ProcessId, Activities, Count, RequestId"
        fileWriter.append(csvHEADER)
        fileWriter.append("\n")
        processList.map {
            try {
                fileWriter.append(it.id?.trim() + ", ")
                fileWriter.append(it.activities.trim() + ", ")
                fileWriter.append(it.count.toString().trim() + ", ")
                fileWriter.append(it.requestId.trim() + "\n")
            } catch (e: Exception) {
                println("Writing CSV error")
            }
        }
        try {
            fileWriter.close()
        } catch (e: Exception) {
            println("File closing Error")
        }
    }
}
