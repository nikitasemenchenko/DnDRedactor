package com.example.dndredactor.data.remote

import com.example.dndredactor.data.remote.dto.ApiListResponseDto
import com.example.dndredactor.data.remote.dto.ArchetypeDto
import com.example.dndredactor.data.remote.dto.ClassDto
import com.example.dndredactor.data.remote.dto.RaceDto
import com.example.dndredactor.data.remote.dto.SubraceDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DndApi {

    @GET("races")
    suspend fun getRaces(): ApiListResponseDto

    @GET("races/{index}")
    suspend fun getRace(
        @Path("index") index: String
    ): RaceDto

    @GET("classes")
    suspend fun getClasses(): ApiListResponseDto

    @GET("classes/{index}")
    suspend fun getClass(
        @Path("index") index: String
    ): ClassDto

    @GET("subclasses/{index}")
    suspend fun getArchetype(
        @Path("index") index: String
    ): ArchetypeDto

    @GET("subraces/{index}")
    suspend fun getSubrace(
        @Path("index") index: String
    ): SubraceDto
}