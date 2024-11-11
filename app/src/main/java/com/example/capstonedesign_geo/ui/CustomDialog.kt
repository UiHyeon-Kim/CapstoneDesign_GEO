package com.example.capstonedesign_geo.ui

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstonedesign_geo.databinding.DialogCustomBinding

class CustomDialog : DialogFragment() {

    private var _binding: DialogCustomBinding? = null
    private val binding get() = _binding!!

    private var confirmDialogInterface: ConfirmDialogInterface? = null
    private var boldText: String = ""
    private var regularText: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogCustomBinding.inflate(inflater, container, false)
        val view = binding.root

        // 배경을 투명하게. 다이얼로그를 둥글게 표현하기 위해 사용
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        boldText = arguments?.getString("boldText") ?: ""
        regularText = arguments?.getString("regularText").orEmpty()

        binding.BoldText.text = boldText
        binding.RegularText.text = regularText

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnConfirm.setOnClickListener {
            confirmDialogInterface?.onConfirmClick("확인")
            dismiss()
        }
        return view
    }

    // 자바에서 사용하기 위한 생성자
    fun setConfirmDialogInterface(confirmDialogInterface: ConfirmDialogInterface) {
        this.confirmDialogInterface = confirmDialogInterface
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(boldText: String, regularText: String): CustomDialog {
            val dialog = CustomDialog()
            val args = Bundle()
            args.putString("boldText", boldText)
            args.putString("regularText", regularText)
            dialog.arguments = args
            return dialog
        }
    }
}

interface ConfirmDialogInterface {
    fun onConfirmClick(item: String)
}