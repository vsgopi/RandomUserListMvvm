package com.example.codingchallenge.model.repository


import com.example.codingchallenge.model.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: UserApi) {

    fun fetchUsers(number: Int): Flow<Result<List<User>>> = flow {
        try {
            val response = api.getUsers(number)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.success(it.results))
                } ?: emit(Result.failure(Exception("No data available")))
            } else {
                emit(Result.failure(Exception("Error fetching users: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
