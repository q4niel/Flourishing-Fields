package dev.q4niel.flourishing_fields

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.File
import java.io.FileInputStream

inline fun<reified T> configInterpreter(fileName: String): T? {
    val data: String = parseFile(fileName) ?: return null;

    return try {
        Gson().fromJson(data, T::class.java);
    }
    catch (e: JsonSyntaxException) {
        null;
    }
}

fun parseFile(fileName: String): String? {
    val file: File = File(fileName);
    if (!file.exists()) return null;

    val builder: StringBuilder = StringBuilder();

    FileInputStream(file).use {
        var byte: Int = it.read();

        while (byte != -1) {
            builder.append(byte.toChar());
            byte = it.read();
        }
    }

    return builder.toString();
}