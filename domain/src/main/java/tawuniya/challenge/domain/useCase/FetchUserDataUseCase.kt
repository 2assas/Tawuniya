package tawuniya.challenge.domain.useCase

import tawuniya.challenge.domain.entity.UserData
import tawuniya.challenge.domain.repository.UserRepository

class FetchUserDataUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<List<UserData>?> {
        return repository.fetchUserData().map { userData ->
            userData?.filter { isValidData(it) }
        }
    }

    private fun isValidData(userData: UserData): Boolean {
        return userData.id > 0 && !userData.name.isNullOrEmpty()
    }
}
