package uz.pdp.provalyutakurslari.models

import java.io.Serializable

data class Course_model(
    var cb_price: String,
    val code: String,
    val date: String,
    val nbu_buy_price: String,
    val nbu_cell_price: String,
    val title: String
) : Serializable