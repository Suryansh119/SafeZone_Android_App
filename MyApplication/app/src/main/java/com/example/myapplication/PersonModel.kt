package com.example.myapplication

class PersonModel {
    var ImageUrl:String=""
    var disp:String="No Description Provided By User"

    constructor(ImageUrl: String, disp: String) {
        this.ImageUrl = ImageUrl
        this.disp = disp
    }
    constructor()

}