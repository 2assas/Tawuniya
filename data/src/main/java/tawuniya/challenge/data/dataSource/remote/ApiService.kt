package tawuniya.challenge.data.dataSource.remote

import retrofit2.Response
import retrofit2.http.GET
import tawuniya.challenge.data.models.UserDataRemote

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<UserDataRemote.UserDataRemoteItem>>
}