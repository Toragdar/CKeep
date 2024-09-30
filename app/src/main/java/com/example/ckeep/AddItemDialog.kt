package com.example.ckeep

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.ckeep.databinding.AddItemDialogBinding

class AddItemDialog : DialogFragment() {

    interface OnAddDialogResultListener {
        fun onAddDialogResult(itemName: String, itemLogin: String, itemPassword: String)
    }

    private var _binding: AddItemDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: OnAddDialogResultListener
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
            listener = context as OnAddDialogResultListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnDialogResultListener")
        }
    }

    private fun onSaveButtonClick(){
        if (binding.itemNameEditText.text.toString().isNotEmpty() &&
            binding.itemLoginEditText.text.toString().isNotEmpty() &&
            binding.itemPasswordEditText.text.toString().isNotEmpty()
        ) {
            listener.onAddDialogResult(
                binding.itemNameEditText.text.toString(),
                binding.itemLoginEditText.text.toString(),
                binding.itemPasswordEditText.text.toString()
            )
            dismiss()
        }
        else {
            Toast.makeText(requireContext(), "FILL ALL FIELDS", Toast.LENGTH_SHORT).show()
        }
    }
}


