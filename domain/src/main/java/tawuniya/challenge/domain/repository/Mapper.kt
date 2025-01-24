package tawuniya.challenge.domain.repository

interface Mapper<Dto, Domain> {
    fun toDomain(dto: Dto): Domain
}
