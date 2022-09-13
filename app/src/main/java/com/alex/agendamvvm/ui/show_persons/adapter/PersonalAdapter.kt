package com.alex.agendamvvm.ui.show_persons.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex.agendamvvm.R
import com.alex.agendamvvm.data.model.Personal
import com.alex.agendamvvm.databinding.ItemListBinding
import com.alex.agendamvvm.ui.form_person.FormActivity
import com.alex.agendamvvm.utils.Constants

class PersonalAdapter(private val personalList:List<Personal>):RecyclerView.Adapter<PersonalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = personalList.get(position)
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return personalList.size
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var binding = ItemListBinding.bind(view)
        var context = view.context
        fun render(personal: Personal){
            binding.tvNombre.text = "${personal.nombre} ${personal.apellidos}"
            binding.tvEmail.text = personal.email
            binding.tvTelefono.text = personal.telefono
            binding.tvEdad.text = personal.edad.toString()

            binding.root.setOnClickListener {
                val intent = Intent(context, FormActivity::class.java)
                intent.putExtra(Constants.OPERATION_KEY,Constants.OPERATION_EDIT)
                intent.putExtra(Constants.ID_PERSONAL_KEY, personal.idEmpleado)
                context.startActivity(intent)
            }
        }
    }
}