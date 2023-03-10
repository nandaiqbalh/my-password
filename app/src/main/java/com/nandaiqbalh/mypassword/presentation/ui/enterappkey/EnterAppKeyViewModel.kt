package com.nandaiqbalh.mypassword.presentation.ui.enterappkey

import androidx.lifecycle.ViewModel
import com.nandaiqbalh.mypassword.data.repository.LocalRepository

class EnterAppKeyViewModel(private val repository: LocalRepository): ViewModel() {

	fun checkIsAppKeyCorrect(appKey: String): Boolean{
		return repository.checkAppKeyIsCorrect(appKey)
	}
}