package com.example.petsall.domain.business

import android.location.Location
import com.example.petsall.data.remote.business.PABusinessDatasource
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class PABusinessRepoImpl @Inject constructor (private val datasource: PABusinessDatasource): PABusinessRepo {
    override suspend fun getDataBusiness(userLocate: Location): List<DocumentSnapshot?> = datasource.getDataBusiness(userLocate = userLocate)
}