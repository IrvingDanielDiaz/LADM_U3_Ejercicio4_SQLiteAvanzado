package mx.edu.ittepic.ladm_u3_ejercicio4_sqliteavanzado

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE TRABAJADOR(IDTRABAJADOR INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NOMBRE VARCHAR(200), " +
                "PUESTO VARCHAR(200)," +
                "SUELDO FLOAT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}