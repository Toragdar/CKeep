package com.example.ckeep

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.ckeep.data.Database
import com.example.ckeep.databinding.AddItemDialogBinding
import com.example.ckeep.repositories.ItemRepository
import com.example.ckeep.viewModels.ItemFactory
import com.example.ckeep.viewModels.ItemViewModel

class AddItemDialog : DialogFragment() {

    private var _binding: AddItemDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemViewModel: ItemViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = AddItemDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext(), R.style.CustomDialog)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)

        val itemDao = Database.getInstance(requireContext()).itemDAO
        val itemRepository = ItemRepository(itemDao)
        val itemFactory = ItemFactory(itemRepository)
        itemViewModel = ViewModelProvider(this, itemFactory).get(ItemViewModel::class.java)

        return dialog
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        binding.saveButton.setOnClickListener {
            if (binding.itemNameEditText.text.toString().isNotEmpty() &&
                binding.itemLoginEditText.text.toString().isNotEmpty() &&
                binding.itemPasswordEditText.text.toString().isNotEmpty()
            ) {
                itemViewModel.startInsert(
                    binding.itemNameEditText.text.toString(),
                    binding.itemLoginEditText.text.toString(),
                    binding.itemPasswordEditText.text.toString()
                )
                Log.d("Dialog Fragment", "Start closing")
                dismiss()
                Log.d("Dialog Fragment", "Finish closing")
            } else {
                Toast.makeText(requireContext(), "FILL ALL FIELDS", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        (activity as? MainActivity)?.refreshRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


