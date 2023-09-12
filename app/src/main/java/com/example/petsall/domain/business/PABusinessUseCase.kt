package com.example.petsall.domain.business

import com.example.petsall.data.remote.model.BusinessData
import com.example.petsall.data.remote.model.mapToListBusinessDataClass
import com.example.petsall.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PABusinessUseCase @Inject constructor(private val repository: PABusinessRepo)  {

    suspend operator fun invoke(nameListBusiness: String): Flow<Resource<List<BusinessData>>> =
        flow{
            emit(Resource.Loading())
            try {

                val documentSnapshots = repository.getDataBusiness(nameListBusiness = nameListBusiness)
                val businessData = documentSnapshots.mapToListBusinessDataClass().listBusiness
                emit(Resource.Success(businessData))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}