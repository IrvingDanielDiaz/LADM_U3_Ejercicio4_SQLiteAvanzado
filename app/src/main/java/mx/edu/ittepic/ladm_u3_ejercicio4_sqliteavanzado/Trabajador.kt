package mx.edu.ittepic.ladm_u3_ejercicio4_sqliteavanzado

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Trabajador(n:String, p:String, s:Float) {
    var nombre = n
    var puesto = p
    var salario = s
    var id = 0
    var error = -1
    /*
    *  valores de error
    * -----------------------
    * 1 = error en tabla, no se creó o no se conecto a base datos
    * 2 = error no se pudo insertar
    * 3 = NO SE PUDO REALIZAR CONSULTA / TABLA VACÍA
    * 4 = NO SE ENCONTRÓ ID
    * 5 = NO ACTUALIZÓ
    * 6 = NO BORRO
    * */

    val nombreBaseDatos = "empresa"

    var puntero : Context ?= null
    fun asignarPuntero(p:Context){
        puntero = p
    }

    fun insertar():Boolean{
        error = -1
        try {
            var base = BaseDatos(puntero!!,nombreBaseDatos,null,1)
            var insertar = base.writableDatabase
            var datos = ContentValues()
            datos.put("NOMBRE",nombre)
            datos.put("PUESTO",puesto)
            datos.put("SUELDO",salario)
            var respuesta =  insertar.insert("TRABAJADOR","IDTRABAJADOR",datos)
            if(respuesta.toInt() == -1){
                error = 2
                return false
            }


        }catch (e:SQLiteException){
            error = 1
            return false
        }
        return true
    }

    fun mostrarTodos() :ArrayList<Trabajador>{
        var data = ArrayList<Trabajador>()
        error = -1
        try{
            var base = BaseDatos(puntero!!, nombreBaseDatos, null, 1)
            var select = base.readableDatabase

            var columnas  = arrayOf("*")
            var cursor = select.query("TRABAJADOR",columnas,null,null,null,null,null)
            if(cursor.moveToFirst()){
                do{
                    var trabajadorTemporal = Trabajador(cursor.getString(1),cursor.getString(2),cursor.getFloat(3))
                    trabajadorTemporal.id = cursor.getInt(0)
                    data.add(trabajadorTemporal)

                }while(cursor.moveToNext())
            }else{
                error = 3
            }
        }catch (e:SQLiteException){
            error = 1
        }
        return data
    }

    fun buscar(id:String) : Trabajador{
        var trabajaEncontrado = Trabajador("-1","-1",-1f)
        error = -1
        try{
            var base = BaseDatos(puntero!!,nombreBaseDatos,null,1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var idBuscar = arrayOf(id)
            var cursor = select.query("TRABAJADOR", columnas,"IDTRABAJADOR = ?", idBuscar,null,null,null)
            if(cursor.moveToFirst()){
                trabajaEncontrado.id = id.toInt()
                trabajaEncontrado.nombre = cursor.getString(1)
                trabajaEncontrado.puesto = cursor.getString(2)
                trabajaEncontrado.salario = cursor.getFloat(3)
            }else{
                error = 4
            }

        }catch (e:SQLiteException){
            error = 1
        }
    return  trabajaEncontrado
    }

    fun actualizar():Boolean{
        error = -1
        try {
            var base = BaseDatos(puntero!!,nombreBaseDatos,null,1)
            var actualizar = base.writableDatabase
            var datos = ContentValues()
            var idActualizar = arrayOf(id.toString())
            datos.put("NOMBRE",nombre)
            datos.put("PUESTO",puesto)
            datos.put("SUELDO",salario)
            var respuesta =  actualizar.update("TRABAJADOR",datos,"IDTRABAJADOR = ?",idActualizar)
            if(respuesta.toInt() == 0){
                error = 5
                return false
            }
        }catch (e:SQLiteException){
            error = 1
            return false
        }
        return true
    }

    fun eliminar():Boolean{
        error = -1
        try {
            var base = BaseDatos(puntero!!,nombreBaseDatos,null,1)
            var eliminar = base.writableDatabase
            var idEliminar = arrayOf(id.toString())
            var respuesta =  eliminar.delete("TRABAJADOR","IDTRABAJADOR=?",idEliminar)
            if(respuesta.toInt() == 0){
                error = 6
                return false
            }
        }catch (e:SQLiteException){
            error = 1
            return false
        }
        return true
    }


}