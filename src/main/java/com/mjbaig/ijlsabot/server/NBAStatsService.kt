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
        }.doOnError {
            logger.error("Could not find this player", it)
        }
    }

    private fun getAllPlayers(): Mono<Players> {
        return webClient
                .get()
                .uri("http://data.nba.net/10s/prod/v1/2020/players.json").retrieve().bodyToMono(Players::class.java).toMono().doOnError {
                    logger.error("Something went wrong with the player profile request", it)
                }
    }

}


data class Players(
        @JsonProperty("league") val league: League? = null,
)

data class League(
        @JsonProperty("standard") val players: List<Player>? = null,
)

data class Player(
        @JsonProperty("firstName") val firstName: String? = null,
        @JsonProperty("lastName") val lastName: String? = null,
        @JsonProperty("temporaryDisplayName") val temporaryDisplayName: String? = null,
        @JsonProperty("personId") val personId: String? = null,
        @JsonProperty("teamId") val teamId: String? = null,
        @JsonProperty("jersey") val jersey: String? = null,
        @JsonProperty("isActive") val isActive: Boolean? = null,
        @JsonProperty("pos") val position: String? = null,
        @JsonProperty("heightFeet") val heightFeet: String? = null,
        @JsonProperty("heightInches") val heightInches: String? = null,
        @JsonProperty("heightMeters") val heightMeters: String? = null,
        @JsonProperty("weightPounds") val weightPounds: String? = null,
        @JsonProperty("weightKilograms") val weightKilograms: String? = null,
        @JsonProperty("dateOfBirthUTC") val dateOfBirthUTC: String? = null,
        @JsonProperty("teams") val teams: List<Team>? = null,
        @JsonProperty("draft") val draft: Draft? = null,
        @JsonProperty("nbaDebutYear") val nbaDebutYear: String? = null,
        @JsonProperty("yearsPro") val yearsPro: String? = null,
        @JsonProperty("collegeName") val collegeName: String? = null,
        @JsonProperty("lastAffiliation") val lastAffiliation: String? = null,
        @JsonProperty("country") val country: String? = null,
)

data class Team(
        @JsonProperty("teamId") val teamId: String? = null,
        @JsonProperty("seasonStart") val seasonStart: String? = null,
        @JsonProperty("seasonEnd") val seasonEnd: String? = null,
)

data class Draft(
        @JsonProperty("teamId") val teamId: String? = null,
        @JsonProperty("pickNum") val pickNum: String? = null,
        @JsonProperty("roundNum") val roundNum: String? = null,
        @JsonProperty("seasonYear") val seasonYear: String? = null,
)