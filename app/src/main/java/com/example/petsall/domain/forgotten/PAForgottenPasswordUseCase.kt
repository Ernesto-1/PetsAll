package com.example.petsall.domain.forgotten

import com.example.petsall.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PAForgottenPasswordUseCase @Inject constructor(private val repo: PAForgottenPasswordRepo) {
    suspend operator fun invoke(email: String): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repo.forgottenPassword(email)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}