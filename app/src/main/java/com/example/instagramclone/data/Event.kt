package com.example.instagramclone.data

open class Event<out T>(private val content: T) {

    //to check if the event or exception has been handled or not
    var hasBeenHandled = false
        private set

    fun  getContentOrNull(): T? {

        //we are returning the content only if the event or exception is not handled (in other words, if hasBeenHandled = false then we will return content)
        return if (hasBeenHandled) {
            null
        }
        else{
            hasBeenHandled = true
            content
        }
    }

}