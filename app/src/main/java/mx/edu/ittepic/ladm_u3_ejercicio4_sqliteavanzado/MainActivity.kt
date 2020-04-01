package mx.edu.ittepic.ladm_u3_ejercicio4_sqliteavanzado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var listaID = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Base de Datos")
        setContentView(R.layout.activity_main)
        insertar.setOnClickListener {
            var trabajador = Trabajador(nombre.text.toString(),
                                        puesto.text.toString(),
                                        sueldo.text.toString().toFloat())
            trabajador.asignarPuntero(this)
            var resultado = trabajador.insertar()
            if(resultado == true){
                mensaje("SE CAPTURÓ TRABAJADOR")
                nombre.setText("")
                puesto.setText("")
                sueldo.setText("")
            }else{
                when(trabajador.error){
                    1 -> {dialogo("error en tabla, no se creó o no se conecto a base datos")}
                    2 -> {dialogo("error no se pudo insertar")}
                }
            }
            cargarLista()
        }
        cargarLista()
    }

    fun cargarLista(){
        try {
            var conexion = Trabajador("","",0f)
            conexion.asignarPuntero(this)
            var data = conexion.mostrarTodos()

            if(data.size == 0){
                if(conexion.error == 3){
                    dialogo("NO SE PUDO REALIZAR CONSULTA / TABLA VACÍA")
                }
                return
            }
            var total = data.size-1
            var vector = Array<String>(data.size,{""})
            listaID = ArrayList<String>()
            (0..total).forEach{
                var trabajador = data[it]
                var item = trabajador.nombre+"\n"+trabajador.puesto
                vector[it] = item
                listaID.add(trabajador.id.toString())
            }
            lista.adapter = ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,vector)
            lista.setOnItemClickListener{ parent,view,position,id->
                var con = Trabajador("","",0f)
                con.asignarPuntero(this)
                var trabaEncontrado = con.buscar(listaID[position])
                if(con.error == 4){
                    dialogo("NO SE ENCONTRÓ ID")
                    return@setOnItemClickListener
                }
                AlertDialog.Builder(this)
                    .setTitle("¿Qué deseas hacer?")
                    .setMessage("Nombre : ${trabaEncontrado.nombre} \n Puesto: ${trabaEncontrado.puesto} \nSueldo: ${trabaEncontrado.salario}")
                    .setPositiveButton("Editar / Eliminar"){d,i-> cargarEnOtroActivity(trabaEncontrado)}
                    .setNeutralButton("Cancelar"){d,i->}
                    .show()
            }
        }   catch (e: Exception){
            dialogo(e.message.toString())
        }
    }

    private fun cargarEnOtroActivity(t: Trabajador) {
        var intento = Intent(this,Main2Activity::class.java)
        intento.putExtra("nombre",t.nombre)
        intento.putExtra("puesto",t.puesto)
        intento.putExtra("sueldo",t.salario)
        intento.putExtra("id",t.id)
        startActivityForResult(intento,0)
    }

    fun mensaje(s:String){
        Toast.makeText(this,s, Toast.LENGTH_LONG)
            .show()
    }
    fun dialogo(s:String){
        AlertDialog.Builder(this)
            .setTitle("ATENCIÓN").setMessage(s)
            .setPositiveButton("OK"){d,i->}
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cargarLista()
    }

}
