package com.example.ckeep

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ckeep.data.Database
import com.example.ckeep.databinding.AddItemDialogBinding
import com.example.ckeep.repositories.ItemRepository
import com.example.ckeep.viewModels.ItemFactory
import com.example.ckeep.viewModels.ItemViewModel

class AddItemDialog : DialogFragment() {

    interface OnDialogResultListener {
        fun onDialogResult(itemName: String, itemLogin: String, itemPassword: String)
    }

    private var _binding: AddItemDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: OnDialogResultListener
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = AddItemDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext(), R.style.CustomDialog)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)

        return dialog
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        binding.saveButton.setOnClickListener {
            onSaveButtonClick()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as OnDialogResultListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnDialogResultListener")
        }
    }

    private fun onSaveButtonClick(){
        if (binding.itemNameEditText.text.toString().isNotEmpty() &&
            binding.itemLoginEditText.text.toString().isNotEmpty() &&
            binding.itemPasswordEditText.text.toString().isNotEmpty()
        ) {
            listener.onDialogResult(
                binding.itemNameEditText.text.toString(),
                binding.itemLoginEditText.text.toString(),
                binding.itemPasswordEditText.text.toString()
            )
        }
        else {
            Toast.makeText(requireContext(), "FILL ALL FIELDS", Toast.LENGTH_SHORT).show()
        }
        dismiss()
    }
}


