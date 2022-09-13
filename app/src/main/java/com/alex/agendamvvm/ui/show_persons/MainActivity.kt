package com.alex.agendamvvm.ui.show_persons

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex.agendamvvm.databinding.ActivityMainBinding
import com.alex.agendamvvm.ui.form_person.FormActivity
import com.alex.agendamvvm.ui.show_persons.adapter.PersonalAdapter
import com.alex.agendamvvm.utils.Constants
import com.alex.agendamvvm.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get()
        binding.lifecycleOwner = this
        binding.modelo = viewModel
        viewModel.start()

        binding.rvListPersonal.apply {
            layoutManager = LinearLayoutManager(applicationContext)
        }

        viewModel.personalList.observe(this, Observer {
            binding.rvListPersonal.adapter = PersonalAdapter(it!!)
        })

        binding.btnOpenForm.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            intent.putExtra(Constants.OPERATION_KEY, Constants.OPERATION_INSERT)
            startActivity(intent)
        }

        binding.etSearch.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()){
                    viewModel.buscarPersonal()
                }
                if (s.toString().isBlank()){
                    viewModel.start()
                }
            }

        })
    }
}