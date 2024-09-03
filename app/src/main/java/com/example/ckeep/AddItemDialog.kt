package com.example.ckeep

import android.app.Dialog
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

    private lateinit var itemRepository: ItemRepository
    private lateinit var itemFactory: ItemFactory
    private lateinit var itemViewModel: ItemViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = AddItemDialogBinding.inflate(layoutInflater)

        // ТЕСТ ПОДКЛЮЧЕНИЯ БД
        val itemDao = Database.getInstance(requireContext()).itemDAO
        itemRepository = ItemRepository(itemDao)
        itemFactory = ItemFactory(itemRepository)
        itemViewModel = ViewModelProvider(this, itemFactory).get(ItemViewModel::class.java)

        val dialog = Dialog(requireContext(), R.style.CustomDialog)
        dialog.setContentView(binding.root) // Используем binding.root как корневой макет
        dialog.setCancelable(false)

        return dialog
    }

    override fun onStart() {
        super.onStart()

        Log.d("MyDialogFragment", "Dialog started")

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // Теперь обработчики будут работать правильно
        binding.saveButton.setOnClickListener {
            Log.d("MyDialogFragment", "Save button clicked")
            Toast.makeText(requireContext(), "New Account was saved", Toast.LENGTH_SHORT).show()
            if(
                binding.itemNameEditText.text.toString() != "" &&
                binding.itemLoginEditText.text.toString() != "" &&
                binding.itemPasswordEditText.text.toString() != ""
            ){
                itemViewModel.startInsert(
                    binding.itemNameEditText.text.toString(),
                    binding.itemLoginEditText.text.toString(),
                    binding.itemPasswordEditText.text.toString()
                )
                dismiss()
            } else {
                Toast.makeText(requireContext(), "FILL ALL FIELDS", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cancelButton.setOnClickListener {
            Log.d("MyDialogFragment", "Cancel button clicked")
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
