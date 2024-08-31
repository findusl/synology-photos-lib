package de.lehrbaum.lib.synology.photos.dto.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    @SerialName("city")
    val city: String,
    @SerialName("city_id")
    val cityId: String,
    @SerialName("country")
    val country: String,
    @SerialName("country_id")
    val countryId: String,
    @SerialName("county")
    val county: String,
    @SerialName("county_id")
    val countyId: String,
    @SerialName("district")
    val district: String,
    @SerialName("district_id")
    val districtId: String,
    @SerialName("landmark")
    val landmark: String,
    @SerialName("landmark_id")
    val landmarkId: String,
    @SerialName("route")
    val route: String,
    @SerialName("route_id")
    val routeId: String,
    @SerialName("state")
    val state: String,
    @SerialName("state_id")
    val stateId: String,
    @SerialName("town")
    val town: String,
    @SerialName("town_id")
    val townId: String,
    @SerialName("village")
    val village: String,
    @SerialName("village_id")
    val villageId: String
)