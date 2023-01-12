package com.nandaiqbalh.mypassword.di

import android.content.Context
import com.nandaiqbalh.mypassword.data.local.preference.UserPreference
import com.nandaiqbalh.mypassword.data.local.preference.UserPreferenceDataSource
import com.nandaiqbalh.mypassword.data.local.preference.UserPreferenceDataSourceImpl
import com.nandaiqbalh.mypassword.data.repository.LocalRepository
import com.nandaiqbalh.mypassword.data.repository.LocalRepositoryImpl

class ServiceLocator {

	fun provideUserPreference(context: Context): UserPreference{
		return UserPreference(context)
	}

	fun provideUserPreferenceDataSource(context: Context): UserPreferenceDataSource{
		return UserPreferenceDataSourceImpl(provideUserPreference(context))
	}

	fun provideLocalRepository(context: Context):LocalRepository{
		return LocalRepositoryImpl(provideUserPreferenceDataSource(context))
	}
}