package com.alex.agendamvvm.ui.form_person.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogErase(var borrarListener: BorrarListener) : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("¿Borrar Personal")
                .setPositiveButton("Si borrar",
                DialogInterface.OnClickListener{dialog, id ->
                    borrarListener.borrarPersonal()
                })
                .setNegativeButton("Cancel",
                DialogInterface.OnClickListener{dialog, id ->
                    dialog.cancel()
                })
            builder.create()
        }?:throw IllegalStateException("La activity no puede ser null")
    }

    interface BorrarListener{
        fun borrarPersonal()
    }
}