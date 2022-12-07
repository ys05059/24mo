package Util

data class CartItem(
    var count : Int,
    val wine :WineDTO
){
    constructor() :this (1, WineDTO())
    constructor(wine:WineDTO) :this(1,wine.copy())
}
