package io.github.gustins123.questionswearos.tile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.tiles.ActionBuilders.LoadAction
import androidx.wear.tiles.DimensionBuilders
import androidx.wear.tiles.LayoutElementBuilders
import androidx.wear.tiles.ModifiersBuilders
import androidx.wear.tiles.ModifiersBuilders.Clickable
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.ResourceBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.TimelineBuilders
import io.github.gustins123.questionswearos.QuestionRepository
import com.google.android.horologist.compose.tools.LayoutRootPreview
import com.google.android.horologist.tiles.CoroutinesTileService


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
                            .setRoot(tileLayout())
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


    private fun tileLayout(): LayoutElementBuilders.LayoutElement {
        val text = QuestionRepository.getRandomQuestion()


        return  LayoutElementBuilders.Box.Builder()
            .setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER)
            .setWidth(DimensionBuilders.expand())
            .setHeight(DimensionBuilders.expand())
            .addContent(
                LayoutElementBuilders.Text.Builder()
                    .setText(text)
                    .setMaxLines(7)
                    .setModifiers(ModifiersBuilders.Modifiers.Builder()
                        .setClickable(Clickable.Builder()
                            .setId("foo")
                            .setOnClick(LoadAction.Builder().build())
                            .build()
                        ).build()
                    )
                    .build()
            )
            .build()
    }

    @Preview(
        device = Devices.WEAR_OS_SMALL_ROUND,
        showSystemUi = true,
        backgroundColor = 0xff000000,
        showBackground = true
    )
    @Composable
    fun TilePreview() {
        LayoutRootPreview(root = tileLayout())
    }
}

