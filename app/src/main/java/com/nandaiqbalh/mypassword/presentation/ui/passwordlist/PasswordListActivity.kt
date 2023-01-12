package com.nandaiqbalh.mypassword.presentation.ui.passwordlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import com.nandaiqbalh.mypassword.R
import com.nandaiqbalh.mypassword.databinding.ActivityPasswordListBinding
import com.nandaiqbalh.mypassword.di.ServiceLocator
import com.nandaiqbalh.mypassword.presentation.ui.createappkey.CreateAppKeyBottomSheet
import com.nandaiqbalh.mypassword.presentation.ui.createappkey.OnAppKeyChangedListener
import com.nandaiqbalh.mypassword.presentation.ui.enterappkey.EnterAppKeyBottomSheet
import com.nandaiqbalh.mypassword.presentation.ui.enterappkey.OnAppKeyConfirmedListener
import com.nandaiqbalh.mypassword.utils.viewModelFactory

class PasswordListActivity : AppCompatActivity() {

	private val binding: ActivityPasswordListBinding by lazy {
		ActivityPasswordListBinding.inflate(layoutInflater)
	}

	private val viewModel: PasswordListViewModel by viewModelFactory {
		PasswordListViewModel(ServiceLocator.provideLocalRepository(this))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
	}


	override fun onResume() {
		super.onResume()
		binding.root.isVisible = false
		if (viewModel.checkIfAppKeyIsExist()) {
			showDialogEnterAppKey(false) { isPasswordCorrect ->
				if (isPasswordCorrect) {
					binding.root.isVisible = true
				}
			}
		} else {
			showCreateNewAppKeyDialog(false) {
				Toast.makeText(this, "App Key Created", Toast.LENGTH_SHORT).show()
				binding.root.isVisible = true
			}
		}
	}

	private fun changeAppKey() {
		if (viewModel.checkIfAppKeyIsExist()) {
			showDialogEnterAppKey { isPasswordCorrect ->
				if (isPasswordCorrect) {
					showCreateNewAppKeyDialog {
						Toast.makeText(this, "App Key Changed", Toast.LENGTH_SHORT).show()
						binding.root.isVisible = true
					}
				}
			}
		}
	}

	private fun showCreateNewAppKeyDialog(
		isCancelable: Boolean = true,
		onAppKeyChanged: () -> Unit
	) {
		val currentDialog =
			supportFragmentManager.findFragmentByTag(CreateAppKeyBottomSheet::class.java.simpleName)
		if (currentDialog == null) {
			CreateAppKeyBottomSheet().apply {
				setListener(object : OnAppKeyChangedListener {
					override fun onAppKeyChanged() {
						onAppKeyChanged.invoke()
					}
				})
				this.isCancelable = isCancelable
			}.show(supportFragmentManager, CreateAppKeyBottomSheet::class.java.simpleName)
		}
	}

	private fun showDialogEnterAppKey(
		isCancelable: Boolean = true,
		onAppKeyConfirmed: (Boolean) -> Unit
	) {
		val currentDialog =
			supportFragmentManager.findFragmentByTag(EnterAppKeyBottomSheet::class.java.simpleName)
		if (currentDialog == null) {
			EnterAppKeyBottomSheet().apply {
				setListener(object : OnAppKeyConfirmedListener {
					override fun onAppKeyConfirmed(isPasswordCorrect: Boolean) {
						onAppKeyConfirmed.invoke(isPasswordCorrect)
					}
				})
				this.isCancelable = isCancelable
			}.show(supportFragmentManager, EnterAppKeyBottomSheet::class.java.simpleName)
		}
	}


	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		val inflater: MenuInflater = menuInflater
		inflater.inflate(R.menu.menu_home, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle item selection
		return when (item.itemId) {
			R.id.menu_action_change_app_key -> {
				changeAppKey()
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}

}