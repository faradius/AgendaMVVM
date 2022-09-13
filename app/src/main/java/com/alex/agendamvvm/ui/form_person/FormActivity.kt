package com.alex.agendamvvm.ui.form_person

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.alex.agendamvvm.databinding.ActivityFormBinding
import com.alex.agendamvvm.ui.form_person.dialogues.DialogErase
import com.alex.agendamvvm.ui.show_persons.MainActivity
import com.alex.agendamvvm.utils.Constants
import com.alex.agendamvvm.viewmodel.FormViewModel

class FormActivity : AppCompatActivity(), DialogErase.BorrarListener {

    lateinit var binding: ActivityFormBinding
    lateinit var viewModel:FormViewModel
    lateinit var dialogoBorrar: DialogErase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialogoBorrar = DialogErase(this)
        viewModel = ViewModelProvider(this).get()
        viewModel.operacion = intent.getStringExtra(Constants.OPERATION_KEY)!!
        binding.modelo = viewModel
        binding.lifecycleOwner = this

        viewModel.resultadoOperacion.observe(this, Observer { estadoOperacion->
            if (estadoOperacion){
                mostrarMensaje("Operación Exitosa")
                irAlIncio()
            }else{
                mostrarMensaje("Ocurrio un Error")
            }
        })

        if(viewModel.operacion.equals(Constants.OPERATION_EDIT)){
            viewModel.id.value = intent.getLongExtra(Constants.ID_PERSONAL_KEY, 0)
            viewModel.cargarDatos()
            binding.linearEditar.visibility = View.VISIBLE
            binding.btnGuardar.visibility = View.GONE
        }else{
            binding.linearEditar.visibility = View.GONE
            binding.btnGuardar.visibility = View.VISIBLE
        }
        binding.btnBorrar.setOnClickListener {
            mostrarDialogo()
        }
    }

    private fun mostrarDialogo() {
        dialogoBorrar.show(supportFragmentManager,"Dialogo Borrar")
    }

    private fun irAlIncio() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun mostrarMensaje(s: String) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }

    override fun borrarPersonal() {
        viewModel.eliminarPersonal()
    }
}