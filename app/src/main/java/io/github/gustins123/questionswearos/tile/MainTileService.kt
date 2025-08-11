package io.github.gustins123.questionswearos.tile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.tiles.ModifiersBuilders
import androidx.wear.tiles.ModifiersBuilders.Clickable
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.ResourceBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.TimelineBuilders
import io.github.gustins123.questionswearos.QuestionRepository
import com.google.android.horologist.compose.tools.LayoutRootPreview
import com.google.android.horologist.tiles.CoroutinesTileService
import androidx.wear.tiles.LayoutElementBuilders;
import androidx.wear.tiles.LayoutElementBuilders.*;
import androidx.wear.tiles.ModifiersBuilders.*;
import androidx.wear.tiles.DimensionBuilders;
import androidx.wear.tiles.*
import androidx.wear.tiles.ActionBuilders.LoadAction
import androidx.wear.tiles.DeviceParametersBuilders.DeviceParameters
import androidx.wear.tiles.LayoutElementBuilders.*
import androidx.wear.tiles.material.layouts.PrimaryLayout


private const val RESOURCES_VERSION = "0"

/**
 * Skeleton for a tile with no images.
 */
class MainTileService : CoroutinesTileService() {

    override suspend fun resourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ): ResourceBuilders.Resources {
        return ResourceBuilders.Resources.Builder()
            .setVersion(RESOURCES_VERSION)
            .build()
    }

    override suspend fun tileRequest(requestParams: RequestBuilders.TileRequest): TileBuilders.Tile {
        QuestionRepository.loadQuestions(this)
        val singleTileTimeline = TimelineBuilders.Timeline.Builder()
            .addTimelineEntry(
                TimelineBuilders.TimelineEntry.Builder()
                    .setLayout(
                        LayoutElementBuilders.Layout.Builder()
                            .setRoot(tileLayout(requestParams.deviceParameters!!))
                            .build()
                    )
                    .build()
            )
            .build()

        return TileBuilders.Tile.Builder()
            .setFreshnessIntervalMillis(1000*60)
            .setResourcesVersion(RESOURCES_VERSION)
            .setTimeline(singleTileTimeline)
            .build()
    }


    private fun tileLayout(deviceParameters: DeviceParameters): LayoutElementBuilders.LayoutElement {
        val text = QuestionRepository.getRandomQuestion()


        val primaryLayout = PrimaryLayout.Builder(deviceParameters)
            .setContent(
                Text.Builder()
                    .setText(text)
                    .setMaxLines(7)
                    .build()
            )
            .build()

        return LayoutElementBuilders.Box.Builder()
            .setWidth(DimensionBuilders.expand())
            .setHeight(DimensionBuilders.expand())
            .setModifiers(
                ModifiersBuilders.Modifiers.Builder()
                    .setClickable(
                        Clickable.Builder()
                            .setId("tile_click")
                            .setOnClick(LoadAction.Builder().build())
                            .build()
                    )
                    .build()
            )
            .addContent(primaryLayout)
            .build()
    }
}

