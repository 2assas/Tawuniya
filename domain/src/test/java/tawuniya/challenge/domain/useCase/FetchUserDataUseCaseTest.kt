package tawuniya.challenge.domain.useCase

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import tawuniya.challenge.domain.entity.UserData
import tawuniya.challenge.domain.repository.UserRepository

class FetchUserDataUseCaseTest {

    private val repository: UserRepository = mock(UserRepository::class.java)
    private val fetchUserDataUseCase = FetchUserDataUseCase(repository)

    @Test
    fun `invoke should return filtered user data when repository fetches successfully`() = runTest {
        // Arrange
        val userDataList = listOf(
            UserData(
                id = 1,
                name = "John Doe",
                username = "johndoe",
                company = "Tech Inc",
                email = "johndoe@example.com",
                phone = "123456789",
                website = "johndoe.com",
                address = "123 Main St"
            ),
            UserData(
                id = -1,
                name = null,
                username = "invalid",
                company = "",
                email = "invalid-email",
                phone = "",
                website = "",
                address = ""
            )
        )
        `when`(repository.fetchUserData()).thenReturn(Result.success(userDataList))

        // Act
        val result = fetchUserDataUseCase()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        verify(repository).fetchUserData()
    }

    @Test
    fun `invoke should return empty list when repository returns no valid data`() = runTest {
        // Arrange
        val userDataList = listOf(
            UserData(
                id = -1,
                name = null,
                username = "invalid",
                company = "",
                email = "invalid-email",
                phone = "",
                website = "",
                address = ""
            )
        )
        `when`(repository.fetchUserData()).thenReturn(Result.success(userDataList))

        // Act
        val result = fetchUserDataUseCase()

        // Assert
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
        verify(repository).fetchUserData()
    }

    @Test
    fun `invoke should return null when repository returns null`() = runTest {
        // Arrange
        `when`(repository.fetchUserData()).thenReturn(Result.success(null))

        // Act
        val result = fetchUserDataUseCase()

        // Assert
        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
        verify(repository).fetchUserData()
    }

    @Test
    fun `invoke should throw exception when repository fails`() = runTest {
        // Arrange
        val exception = RuntimeException("API error")
        `when`(repository.fetchUserData()).thenThrow(exception)

        // Act & Assert
        val thrown = assertThrows<RuntimeException> {
            runBlocking { fetchUserDataUseCase() }
        }
        assertEquals("API error", thrown.message)
        verify(repository).fetchUserData()
    }
}

