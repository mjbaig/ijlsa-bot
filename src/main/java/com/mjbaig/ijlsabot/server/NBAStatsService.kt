package com.mjbaig.ijlsabot.server

import com.fasterxml.jackson.annotation.JsonProperty
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class NBAStatsService(var webClient: WebClient) {

    private val logger = getLogger(NBAStatsService::class.java)


    fun getPlayerByName(firstName: String, lastName: String): Mono<Player> {
        return getAllPlayers().flatMap {
            it?.league?.players?.first { player ->
                player.firstName.equals(firstName, ignoreCase = true) && player.lastName.equals(lastName, ignoreCase = true)
            }.toMono()
        }
    }

    private fun getAllPlayers(): Mono<Players> {
        return webClient
                .get()
                .uri("http://data.nba.net/10s/prod/v1/2020/players.json").retrieve().bodyToMono(Players::class.java).toMono().doOnError {
                    logger.error("Player Not Found", it)
                }
    }

}


data class Players(
        @JsonProperty("league") val league: League?
)

data class League(
        @JsonProperty("standard") val players: List<Player>?
)

data class Player(
        @JsonProperty("firstName") val firstName: String?,
        @JsonProperty("lastName") val lastName: String?,
        @JsonProperty("temporaryDisplayName") val temporaryDisplayName: String?,
        @JsonProperty("personId") val personId: String?,
        @JsonProperty("teamId") val teamId: String?,
        @JsonProperty("jersey") val jersey: String?,
        @JsonProperty("isActive") val isActive: Boolean?,
        @JsonProperty("pos") val position: String?,
        @JsonProperty("heightFeet") val heightFeet: String?,
        @JsonProperty("heightInches") val heightInches: String?,
        @JsonProperty("heightMeters") val heightMeters: String?,
        @JsonProperty("weightPounds") val weightPounds: String?,
        @JsonProperty("weightKilograms") val weightKilograms: String?,
        @JsonProperty("dateOfBirthUTC") val dateOfBirthUTC: String?,
        @JsonProperty("teams") val teams: List<Team>,
        @JsonProperty("draft") val draft: Draft,
        @JsonProperty("nbaDebutYear") val nbaDebutYear: String?,
        @JsonProperty("yearsPro") val yearsPro: String?,
        @JsonProperty("collegeName") val collegeName: String?,
        @JsonProperty("lastAffiliation") val lastAffiliation: String?,
        @JsonProperty("country") val country: String?,
)

data class Team(
        @JsonProperty("teamId") val teamId: String?,
        @JsonProperty("seasonStart") val seasonStart: String?,
        @JsonProperty("seasonEnd") val seasonEnd: String?,
)

data class Draft(
        @JsonProperty("teamId") val teamId: String?,
        @JsonProperty("pickNum") val pickNum: String?,
        @JsonProperty("roundNum") val roundNum: String?,
        @JsonProperty("seasonYear") val seasonYear: String?,
)