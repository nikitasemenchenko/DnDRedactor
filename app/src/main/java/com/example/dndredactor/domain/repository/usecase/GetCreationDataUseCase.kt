package com.example.dndredactor.domain.repository.usecase

import com.example.dndredactor.data.model.CharacterClass
import com.example.dndredactor.data.model.Race
import com.example.dndredactor.domain.repository.CreationRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class GetCreationDataUseCase @Inject constructor(
    private val creationRepository: CreationRepository
) {
    suspend operator fun invoke(): CreationData {
        return supervisorScope {
            val racesAsync = async {
                creationRepository.getRaces()
            }

            val classesAsync = async {
                creationRepository.getClasses()
            }

            CreationData(
                races = racesAsync.await(),
                classes = classesAsync.await()
            )
        }
    }
}

data class CreationData(
    val races: List<Race>,
    val classes: List<CharacterClass>
)