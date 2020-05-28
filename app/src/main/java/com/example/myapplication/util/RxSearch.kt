package com.example.myapplication.util

import androidx.appcompat.widget.SearchView
import rx.Observable
import rx.subjects.BehaviorSubject

class RxSearch {
    companion object{
        fun  fromSearchView(searchView: SearchView): Observable<String> {
            val subject = BehaviorSubject.create("")
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    subject.onCompleted()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isNotEmpty()) {
                        subject.onNext(newText)
                    }
                    return true
                }
            })
            return subject
        }
    }

}