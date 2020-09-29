package com.will.pviewer.network

import com.squareup.moshi.Json

/**
 * created  by will on 2020/9/24 11:51
 */
data class SeriesQueryResponse(
    @Json(name = "total")
    val total: Int,
    @Json(name = "list")
    val list: List<SeriesResponse>
) {

}