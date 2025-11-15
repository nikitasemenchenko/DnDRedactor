package com.example.dndredactor.data.remote

import com.example.dndredactor.data.model.Character
import com.example.dndredactor.data.model.CharacterDetails
import com.example.dndredactor.data.model.CharacterSkillsResponse
import com.example.dndredactor.data.model.CharacterStatsResponse
import com.example.dndredactor.data.model.CreateCharacterRequest
import com.example.dndredactor.data.model.CreatedCharacterResponse
import com.example.dndredactor.data.model.DeleteResponse
import com.example.dndredactor.data.model.MarkSavingThrowProficiencyRequest
import com.example.dndredactor.data.model.MarkSavingThrowProficiencyResponse
import com.example.dndredactor.data.model.SavingThrows
import com.example.dndredactor.data.model.StatsUpdateRequest
import com.example.dndredactor.data.model.UpdateSkillRequest
import com.example.dndredactor.data.model.UpdateSkillResponse
import com.example.dndredactor.data.model.UpdatedStatsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CharacterApi {
    @GET("characters")
    suspend fun getCharacters(): Response<List<Character>>

    @GET("characters/{id}")
    suspend fun getCharacterDetails(
        @Path("id") id: String
    ): Response<CharacterDetails>

    @DELETE("characters/{id}")
    suspend fun deleteCharacter(
        @Path("id") id: String
    ): Response<DeleteResponse>

    @POST("characters")
    suspend fun createCharacter(
        @Body character: CreateCharacterRequest
    ): Response<CreatedCharacterResponse>

    @GET("characters/{id}/stats")
    suspend fun getCharacterStats(
        @Path("id") id: String
    ): Response<CharacterStatsResponse>

    @PATCH("characters/{id}/stats")
    suspend fun updateCharacterStats(
        @Path("id") id: String,
        @Body newStats: StatsUpdateRequest
    ): Response<UpdatedStatsResponse>

    @GET("characters/{id}/skills")
    suspend fun getCharacterSkills(
        @Path("id") id: String
    ): Response<CharacterSkillsResponse>

    @PATCH("characters/{id}/skills/{skillName}")
    suspend fun updateCharacterSkill(
        @Path("id") id: String,
        @Path("skillName") skillName: String,
        @Body skill: UpdateSkillRequest
    ): Response<UpdateSkillResponse>

    @GET("characters/{id}/saving-throws")
    suspend fun getCharacterSavingThrows(
        @Path("id") id: String
    ): Response<SavingThrows>

    @PATCH("characters/{id}/saving-throws/{ability}")
    suspend fun markSavingThrowProficiency(
        @Path("id") id: String,
        @Path("ability") ability: String,
        @Body request: MarkSavingThrowProficiencyRequest
    ): Response<MarkSavingThrowProficiencyResponse>
}