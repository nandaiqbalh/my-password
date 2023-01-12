package com.nandaiqbalh.mypassword.data.repository

import com.nandaiqbalh.mypassword.data.local.preference.UserPreferenceDataSource

interface LocalRepository {

	fun checkAppKeyIsExist(): Boolean
	fun checkAppKeyIsCorrect(appKey: String): Boolean
	fun setAppKey(newAppKey: String)

}

class LocalRepositoryImpl(
	private val userPreferenceDataSource: UserPreferenceDataSource
	) : LocalRepository{
	override fun checkAppKeyIsExist(): Boolean {
		return userPreferenceDataSource.getUserAppKey().isNullOrEmpty().not()
	}

	override fun checkAppKeyIsCorrect(appKey: String): Boolean {
		return userPreferenceDataSource.getUserAppKey().equals(appKey, ignoreCase = true)
	}

	override fun setAppKey(newAppKey: String) {
		userPreferenceDataSource.setUserAppKey(newAppKey)
	}

}