package mx.edu.ittepic.ladm_u3_ejercicio4_sqliteavanzado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {
    var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var extra = intent.extras!!
        ebnombre.setText(extra.getString("nombre"))
        ebpuesto.setText(extra.getString("puesto"))
        ebsueldo.setText(extra.getFloat("sueldo").toString())
        id = extra.getInt("id").toString()
        setTitle("Administrar a "+extra.getString("nombre"))
        actualizar.setOnClickListener {
            var trabajadorActualizar = Trabajador(ebnombre.text.toString(), ebpuesto.text.toString(),ebsueldo.text.toString().toFloat())
            trabajadorActualizar.id = id.toInt()
            trabajadorActualizar.asignarPuntero(this)
            if(trabajadorActualizar.actualizar() == true){
                dialogo("se actualizó")
            }else{
                dialogo("ERROR, NO SE PUDO ACTUALIZAR")
            }
            finish()
        }
        eliminar.setOnClickListener {
            var trabajadorEliminar = Trabajador("","",0f)
            trabajadorEliminar.id = id.toInt()
            trabajadorEliminar.asignarPuntero(this)
            if(trabajadorEliminar.eliminar()){
                dialogo("SE ELIMINÓ")
            }else{
                dialogo("NO SE PUDO ELIMINAR")
            }
            finish()
        }

        regresar.setOnClickListener {
            finish()
        }
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
}
