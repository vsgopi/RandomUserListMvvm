package com.example.codingchallenge.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codingchallenge.R
import com.example.codingchallenge.databinding.ActivityListBinding
import com.example.codingchallenge.model.data.User
import com.example.codingchallenge.model.interfaces.OnUserItemClickListener
import com.example.codingchallenge.utilities.ResponseWrapper
import com.example.codingchallenge.utilities.ViewUtils.showToast
import com.example.codingchallenge.view.adapter.UserAdapter
import com.example.codingchallenge.view.fragment.UserDetailsBottomSheet
import com.example.codingchallenge.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListActivity : FragmentActivity(), OnUserItemClickListener {

    private val userViewModel: UserViewModel by viewModels()
    private val bottomSheetTag = "UserDetailsBottomSheet"
    private val initialListCount = 10
    private lateinit var binding: ActivityListBinding
    private lateinit var adapter: UserAdapter
    private var filterUserList: List<User> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setupRecyclerView()
        attachObserver()
        setupSearch()
    }

    // init the view
    private fun initView() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_list)
        binding.lifecycleOwner = this
        binding.viewModel = userViewModel
        binding.swipeRefreshLayout.setOnRefreshListener {
            fetchUserList()
        }
        binding.ivListCount.setOnClickListener {
            showInputDialog()
        }
    }


    // Attach the observer of viewmodel
    private fun attachObserver() {
        lifecycleScope.launch {
            // Collect users from the ViewModel
            userViewModel.users.observe(this@ListActivity) { resource ->
                when (resource) {
                    is ResponseWrapper.Success -> {
                        // Update UI with the user list
                        val users = resource.data ?: emptyList()
                        filterUserList = users
                        adapter.updateList(users)
                    }

                    is ResponseWrapper.Error -> {
                        // Show error message
                        resource.message?.let { showToast(it, Toast.LENGTH_SHORT) }
                    }

                    is ResponseWrapper.Loading -> {
                        // Show loading state
                        binding.swipeRefreshLayout.isRefreshing = true
                    }
                }
            }

        }
        fetchUserList()
    }

    // Set up RecyclerView with adapter
    private fun setupRecyclerView() {
        adapter = UserAdapter(emptyList(), this)
        binding.userRecyclerView.adapter = adapter
        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    // Open the user details in bottom sheet
    override fun onUserItemClick(user: User) {
        val bottomSheet = UserDetailsBottomSheet(user)
        bottomSheet.show(supportFragmentManager, bottomSheetTag)
    }

    // Get user list from api
    private fun fetchUserList() {
        userViewModel.fetchUsers(initialListCount)
    }

    // Setup SearchView to filter users
    private fun setupSearch() {
        binding.userSearchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filterUsers(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

    }

    // Update adapter with filtered list
    private fun filterUsers(query: String?) {
        val filteredList = if (!TextUtils.isEmpty(query)) {
            filterUserList.filter { user ->
                user.name.first.contains(query!!, true) || user.name.last.contains(query, true)
            }
        } else {
            filterUserList
        }
        adapter.updateList(filteredList)
    }

    // Show the Alert to enter the list count value
    private fun showInputDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_add_list_count, null, false)
        val etListCount: EditText = view.findViewById(R.id.etAddCount)
        val btnFetch: Button = view.findViewById(R.id.btnFetchList)

        val dialog = AlertDialog.Builder(this, R.style.customAlertTheme)
            .setTitle(getString(R.string.fetch_users))
            .setView(view)
            .create()

        btnFetch.setOnClickListener {
            val userCount = etListCount.text.toString().toIntOrNull() ?: 0
            if (userCount > 0) {
                userViewModel.fetchUsers(userCount)
                dialog.dismiss()
            } else {
                showToast(getString(R.string.enter_valid_number_error_msg))
            }
        }

        dialog.show()
    }
}

