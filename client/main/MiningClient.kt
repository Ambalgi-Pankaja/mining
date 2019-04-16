package com.acomodeo.mining.client

import com.acomodeo.mining.model.Event
import com.acomodeo.mining.model.Prom
import com.acomodeo.semanticid.SemanticId
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MiningClient {

    @GET("events")
    fun findEventById(
        @Path("id") id: SemanticId
    ): Call<Event>

    @GET("events/{id}")
    fun findAllByRequestId(
        @Path("id") requestId: String
    ): Call<List<Event>>

    @GET("prom/{id}")
    fun findById(
        @Path("id") id: SemanticId
    ): Call<Prom>

    @GET("prom")
    fun findByCriteria(
        @Query("criteria") criteria: String
    ): Call<List<Prom>>
}