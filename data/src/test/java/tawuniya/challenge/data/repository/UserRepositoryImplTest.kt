package tawuniya.challenge.data.repository

import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import retrofit2.Response
import tawuniya.challenge.data.dataSource.remote.ApiService
import tawuniya.challenge.data.mappers.UserDataMapper
import tawuniya.challenge.data.models.UserDataRemote
import tawuniya.challenge.domain.entity.UserData
import tawuniya.challenge.domain.repository.UserStorage

class UserRepositoryImplTest {

    private lateinit var userRepository: UserRepositoryImpl
    private val userApiService: ApiService = mock()
    private val userStorage: UserStorage = mock()
    private val userDataMapper: UserDataMapper = mock()

    @BeforeEach
    fun setUp() {
        userRepository = UserRepositoryImpl(userApiService, userStorage, userDataMapper)
    }

    @Test
    fun `fetchUserData should return mapped users with liked status when API call is successful`() =
        runTest {
            // Arrange
            val remoteUsers = listOf(
                UserDataRemote.UserDataRemoteItem(id = 1, name = "John Doe"),
                UserDataRemote.UserDataRemoteItem(id = 2, name = "Jane Doe")
            )
            val domainUsers = listOf(
                UserData(id = 1, name = "John Doe", isLiked = false),
                UserData(id = 2, name = "Jane Doe", isLiked = false)
            )
            val likedUsers = setOf(1L)

            `when`(userApiService.getUsers()).thenReturn(Response.success(remoteUsers))
            `when`(userDataMapper.toDomain(remoteUsers)).thenReturn(domainUsers)
            `when`(userStorage.getLikedUsers()).thenReturn(likedUsers)

            // Act
            val result = userRepository.fetchUserData()

            // Assert
            assertTrue(result.isSuccess)
            val data = result.getOrNull()
            assertNotNull(data)
            assertEquals(2, data!!.size)
            assertTrue(data[0].isLiked)
            assertFalse(data[1].isLiked)

            verify(userApiService).getUsers()
            verify(userStorage).getLikedUsers()
        }

    @Test
    fun `fetchUserData should return empty list when API response is empty`() = runTest {
        // Arrange
        `when`(userApiService.getUsers()).thenReturn(Response.success(emptyList()))
        `when`(userDataMapper.toDomain(emptyList())).thenReturn(emptyList())

        // Act
        val result = userRepository.fetchUserData()

        // Assert
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }

    @Test
    fun `fetchUserData should return failure when API call fails`() = runTest {
        // Arrange
        `when`(userApiService.getUsers()).thenThrow(RuntimeException("Network Error"))

        // Act
        val result = userRepository.fetchUserData()

        // Assert
        assertTrue(result.isFailure)
        assertEquals("Network Error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fetchUserData should return failure when API response is not successful`() = runTest {
        // Arrange
        `when`(userApiService.getUsers()).thenReturn(
            Response.error(
                500,
                "".toResponseBody("application/json".toMediaTypeOrNull())
            )
        )

        // Act
        val result = userRepository.fetchUserData()

        // Assert
        assertTrue(result.isFailure)
        assertEquals("Failed to fetch data", result.exceptionOrNull()?.message)
    }

    @Test
    fun `likeUser should store user ID in local storage`() = runTest {
        // Arrange
        val userId = 1L

        // Act
        userRepository.likeUser(userId)

        // Assert
        verify(userStorage).likeUser(userId)
    }

    @Test
    fun `unlikeUser should remove user ID from local storage`() = runTest {
        // Arrange
        val userId = 1L

        // Act
        userRepository.unlikeUser(userId)

        // Assert
        verify(userStorage).unlikeUser(userId)
    }

    @Test
    fun `fetchUserData should return all users with isLiked false when storage is empty`() =
        runTest {
            // Arrange
            val remoteUsers = listOf(UserDataRemote.UserDataRemoteItem(id = 1, name = "John Doe"))
            val domainUsers = listOf(UserData(id = 1, name = "John Doe", isLiked = false))

            `when`(userApiService.getUsers()).thenReturn(Response.success(remoteUsers))
            `when`(userDataMapper.toDomain(remoteUsers)).thenReturn(domainUsers)
            `when`(userStorage.getLikedUsers()).thenReturn(emptySet())

            // Act
            val result = userRepository.fetchUserData()

            // Assert
            assertTrue(result.isSuccess)
            val data = result.getOrNull()
            assertNotNull(data)
            assertFalse(data!![0].isLiked)

            verify(userStorage).getLikedUsers()
        }
}
