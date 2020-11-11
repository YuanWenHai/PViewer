package com.will.pviewer.extension

/**
 * created  by will on 2020/11/11 12:14
 */
fun <E>MutableList<E>.syncAdd(element: E){
    synchronized(this){
        add(element)
    }
}
fun <E> MutableList<E>.syncRemove(element: E){
    synchronized(this){
        remove(element)
    }
}