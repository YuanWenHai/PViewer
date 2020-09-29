package com.will.pviewer.network

import com.squareup.moshi.Json
import com.will.pviewer.data.Series

data class SeriesResponse(
    @Json(name = "name")
    val name: String,
    @Json(name = "alias")
    val alias: String
) {

    companion object {
        fun toSeries(seriesResponse: SeriesResponse): Series{
            return Series(name = seriesResponse.name,alias = seriesResponse.alias)
        }
        fun toSeriesList(list: List<SeriesResponse>): List<Series>{
            return list.map {
                toSeries(it)
            }
        }
    }
}