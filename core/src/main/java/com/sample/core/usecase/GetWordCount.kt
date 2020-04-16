package com.sample.core.usecase

import com.sample.core.data.Note

class GetWordCount {
    public operator fun invoke(note: Note) = getCount(note.title) + getCount(note.content)

    private fun getCount(word: String): Int {
        return word.split(" ", "\n")
            .filter {
                it.contains(Regex(".*[a-zA-Z].*"))
            }
            .count()
    }

}