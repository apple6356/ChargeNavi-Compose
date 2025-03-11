package com.seo.sesac.data.entity

// https://api.ncloud-docs.com/docs/ai-naver-mapsdirections-driving 참조

data class DirectionEntity(
    val code: Int,
    val message: String,
    val currentDateTime: String, // yyyy-MM-ddTHH:mm:ss
    val route: Route
)

data class Route(
    val trafast: List<RouteInfo>,
    val tracomfort: List<RouteInfo>,
    val traoptimal: List<RouteInfo>,
    val traavoidtoll: List<RouteInfo>,
    val traavoidcaronly: List<RouteInfo>
)

data class RouteInfo(
    val summary: Summary,
    val path: List<List<Double>>,
    val section: List<Section>,
    val guide: List<Guide>
)

data class Summary(
    val start: Start,
    val goal: Goal,
    val distance: Int,
    val duration: Int,
    val departureTime: String,
    val bbox: List<List<Double>>,
    val tollFare: Int,
    val taxiFare: Int,
    val fuelPrice: Int
)

data class Start(
    val location: List<Double>
)

data class Goal(
    val location: List<Double>,
    val dir: Int
)

data class Section(
    val pointIndex: Int,
    val pointCount: Int,
    val distance: Int,
    val name: String,
    val congestion: Int,
    val speed: Int
)

data class Guide(
    val pointIndex: Int,
    val type: Int,
    val instructions: String,
    val distance: Int,
    val duration: Int
)