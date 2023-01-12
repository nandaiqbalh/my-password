package com.nandaiqbalh.mypassword.presentation.ui.createappkey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nandaiqbalh.mypassword.R
import com.nandaiqbalh.mypassword.databinding.FragmentCreateAppKeyBottomSheetBinding
import com.nandaiqbalh.mypassword.di.ServiceLocator
import com.nandaiqbalh.mypassword.utils.viewModelFactory

class CreateAppKeyBottomSheet : BottomSheetDialogFragment() {

	private lateinit var binding: FragmentCreateAppKeyBottomSheetBinding

	private var listener: OnAppKeyChangedListener? = null

	private val viewModel: CreateAppKeyViewModel by viewModelFactory {
		CreateAppKeyViewModel(ServiceLocator.provideLocalRepository(requireContext()))
	}

	fun setListener(listener: OnAppKeyChangedListener){
		this.listener = listener
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		binding = FragmentCreateAppKeyBottomSheetBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setClickListener()
	}

	private fun setClickListener(){
		binding.btnConfirmAppKey.setOnClickListener {
			changeAppKey()
		}
	}

	fun changeAppKey(){
		if (validateForm()){
			val newAppKey = binding.etAppKey.text.toString().trim()
			context?.let {
				viewModel.setAppKey(newAppKey)
			}

			listener?.onAppKeyChanged()
			dismiss()
		}
	}

	private fun validateForm():Boolean{
		val appKey = binding.etAppKey.text.toString()
		val confirmedAppKey = binding.etConfirmedAppKey.text.toString()

		var isFormValid = true

		if (appKey.isEmpty()){
			isFormValid = false
			binding.tilAppKey.isErrorEnabled = true
			binding.tilAppKey.error = "This field must not be empty!"
		} else {
			binding.tilAppKey.isErrorEnabled = false
		}

		if (confirmedAppKey.isEmpty()){
			isFormValid = false
			binding.tilConfirmedAppKey.isErrorEnabled = true
			binding.tilConfirmedAppKey.error = "This field must not be empty!"
		} else {
			binding.tilConfirmedAppKey.isErrorEnabled = false
		}

		if (appKey!=confirmedAppKey){
			isFormValid = false
			Toast.makeText(context, "App key doesn't match!", Toast.LENGTH_LONG).show()
		}

		return isFormValid
	}
}

interface OnAppKeyChangedListener{
	fun onAppKeyChanged()
}