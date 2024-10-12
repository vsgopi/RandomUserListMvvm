package com.example.codingchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallenge.utilities.ResponseWrapper
import com.example.codingchallenge.model.data.User
import com.example.codingchallenge.model.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _users = MutableLiveData<ResponseWrapper<List<User>>>()
    val users: LiveData<ResponseWrapper<List<User>>> = _users

    // Fetch users and handle response with Resource
    fun fetchUsers(number: Int) {
        _isRefreshing.value = true
        _users.value = ResponseWrapper.Loading()

        viewModelScope.launch {
            userRepository.fetchUsers(number).collect { result ->
                result.onSuccess { userList ->
                    _users.value = ResponseWrapper.Success(userList)
                    _isRefreshing.value = false
                }.onFailure { exception ->
                    _users.value = ResponseWrapper.Error(exception.message ?: "Unknown error")
                    _isRefreshing.value = false
                }
            }
        }
    }
}
