package com.example.ckeep

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class AddItemDialog : DialogFragment() {

    //TODO Перепилить под байндинг нормально + настроить  вызов диалога и сохранение данных в БД

    private var onSaveListener: ((String, String, String) -> Unit)? = null

    fun setOnSaveListener(listener: (String, String, String) -> Unit) {
        onSaveListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_item_dialog, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext(), R.style.CustomDialog)
        dialog.setContentView(R.layout.add_item_dialog)
        dialog.setCancelable(false)
        return dialog
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val editText1 = dialog?.findViewById<EditText>(R.id.itemNameEditText)
        val editText2 = dialog?.findViewById<EditText>(R.id.itemLoginEditText)
        val editText3 = dialog?.findViewById<EditText>(R.id.itemPasswordEditText)
        val saveButton = dialog?.findViewById<Button>(R.id.saveButton)
        val cancelButton = dialog?.findViewById<Button>(R.id.cancelButton)

        saveButton?.setOnClickListener {
            val input1 = editText1?.text.toString()
            val input2 = editText2?.text.toString()
            val input3 = editText3?.text.toString()

            onSaveListener?.invoke(input1, input2, input3)
            dismiss()
        }

        cancelButton?.setOnClickListener {
            dismiss()
        }
    }
}