package com.example.ckeep

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.ckeep.databinding.AddItemDialogBinding
import com.example.ckeep.databinding.ConfirmDeleteDialogBinding
import com.example.ckeep.models.ItemModel

class ConfirmDeleteDialog(private val itemModel: ItemModel) : DialogFragment() {

    interface ConfirmDeleteListener {
        fun onDeleteConfirmed(itemModel: ItemModel)
    }

    private var _binding: ConfirmDeleteDialogBinding? = null
    private val binding get() = _binding!!

    private var listener: ConfirmDeleteListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = ConfirmDeleteDialogBinding.inflate(layoutInflater)
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

        binding.buttonYes.setOnClickListener {
            listener?.onDeleteConfirmed(itemModel)
            dismiss()
        }

        binding.buttonNo.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ConfirmDeleteListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement ConfirmDeleteListener")
        }
    }
}
