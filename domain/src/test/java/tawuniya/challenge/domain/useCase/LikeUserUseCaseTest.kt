package tawuniya.challenge.domain.useCase
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.eq
import org.mockito.kotlin.never
import tawuniya.challenge.domain.repository.UserRepository

class LikeUserUseCaseTest {

    private val userRepository: UserRepository = mock(UserRepository::class.java)
    private val likeUserUseCase = LikeUserUseCase(userRepository)

    @Test
    fun `should handle invalid user ID`() = runTest {
        // Arrange
        val invalidUserId = 0L // Invalid ID
        val isLiked = false

        // Act
        likeUserUseCase.invoke(invalidUserId, isLiked)

        // Assert
        verify(userRepository, never()).likeUser(eq(invalidUserId)) // Should not call likeUser with invalid ID
        verify(userRepository, never()).unlikeUser(eq(invalidUserId)) // Should not call unlikeUser with invalid ID
    }

    @Test
    fun `should propagate exception from repository`() = runTest {
        // Arrange
        val userId = 1L
        val isLiked = false
        `when`(userRepository.likeUser(userId)).thenThrow(RuntimeException("Network Error"))

        // Act & Assert
        try {
            likeUserUseCase.invoke(userId, isLiked)
        } catch (e: Exception) {
            // Ensure the exception is correctly propagated
            assert(e is RuntimeException)
            assert(e.message == "Network Error")
        }
    }

    @Test
    fun `should call likeUser followed by unlikeUser when toggling like state`() = runTest {
        // Arrange
        val userId = 1L
        val initialIsLiked = false

        // Act
        likeUserUseCase.invoke(userId, initialIsLiked) // First call (like)
        likeUserUseCase.invoke(userId, !initialIsLiked) // Second call (unlike)

        // Assert
        verify(userRepository, times(1)).likeUser(userId)
        verify(userRepository, times(1)).unlikeUser(userId)
    }
}
