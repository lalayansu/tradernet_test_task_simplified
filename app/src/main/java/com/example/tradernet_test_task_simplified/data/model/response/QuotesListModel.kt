package com.example.tradernet_test_task_simplified.data.model.response


import com.google.gson.annotations.SerializedName

data class QuotesListModel(
    @SerializedName("buttons")
    val buttonsModel: ButtonsModel? = null,
    @SerializedName("tickers")
    val tickers: List<String?>? = null
)

data class ButtonsModel(
    @SerializedName("blocks")
    val blockModels: List<BlockModel?>? = null
)

data class ItemModel(
    @SerializedName("action")
    val action: String? = null,
    @SerializedName("color")
    val color: Any? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("url")
    val url: Any? = null
)

data class BlockModel(
    @SerializedName("color")
    val color: Any? = null,
    @SerializedName("group")
    val group: Int? = null,
    @SerializedName("icon")
    val icon: String? = null,
    @SerializedName("items")
    val itemModels: List<ItemModel?>? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("type")
    val type: String? = null
)