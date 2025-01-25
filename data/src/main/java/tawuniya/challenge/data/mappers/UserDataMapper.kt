package tawuniya.challenge.data.mappers

import tawuniya.challenge.data.models.UserDataRemote
import tawuniya.challenge.domain.entity.UserData
import tawuniya.challenge.domain.repository.Mapper

class UserDataMapper : Mapper<List<UserDataRemote.UserDataRemoteItem?>, List<UserData>> {
    override fun toDomain(dto: List<UserDataRemote.UserDataRemoteItem?>) =
        dto.map { remoteItem ->
            UserData(
                id = remoteItem?.id?.toLong() ?: 0L,
                name = remoteItem?.name.orEmpty(),
                username = remoteItem?.username.orEmpty(),
                company = remoteItem?.company?.name.orEmpty(),
                email = remoteItem?.email.orEmpty(),
                phone = remoteItem?.phone.orEmpty(),
                website = remoteItem?.website.orEmpty(),
                address = formatAddress(remoteItem?.address),
                isLiked = false  // Default value, will be set later from local storage
            )
        }
}

private fun formatAddress(address: UserDataRemote.UserDataRemoteItem.Address?): String {
    return address?.let {
        "${it.street.orEmpty()}, ${it.suite.orEmpty()}, ${it.city.orEmpty()}"
    } ?: "N/A"
}

