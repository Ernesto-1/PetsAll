package com.example.petsall.domain.forgotten

import com.example.petsall.data.remote.forgotten.PAForgottenPasswordDataSource
import javax.inject.Inject

class PAForgottenPasswordRepoImpl @Inject constructor(val dataSource:PAForgottenPasswordDataSource): PAForgottenPasswordRepo {
    override suspend fun forgottenPassword(email: String): Boolean = dataSource.forgottenPassword(email)
}