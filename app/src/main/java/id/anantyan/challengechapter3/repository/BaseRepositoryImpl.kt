package id.anantyan.challengechapter3.repository

import id.anantyan.challengechapter3.model.AlphabetModel
import id.anantyan.challengechapter3.model.WordsModel

class BaseRepositoryImpl: BaseRepository {
    override fun listAlphabet(): List<AlphabetModel> {
        val alphabets = ('A'..'Z')
        val items = alphabets.map { letter ->
            val wordsForLetter = listWord(letter.toString()).take(3)
            AlphabetModel(letter.toString(), wordsForLetter)
        }
        return items
    }

    override fun listWord(key: String): List<WordsModel> {
        val wordsList = listOf(
            "Apple", "Art", "Adventure", "Algorithm", "Airplane",
            "Basketball", "Beach", "Biology", "Ballet", "Books",
            "Coding", "Cooking", "Climbing", "Chemistry", "Cinema",
            "Dance", "Design", "Drawing", "Drama", "Diving",
            "Education", "Engineering", "Environment", "Exploration", "Economics",
            "Fitness", "Football", "Fishing", "Fashion", "Film",
            "Gardening", "Guitar", "Games", "Geography", "Genetics",
            "Health", "History", "Hiking", "Hockey", "Horror",
            "Ice Cream", "Internet", "Innovation", "Imagination", "Identity",
            "Jazz", "Journey", "Justice", "Jigsaw", "Juggling",
            "Knowledge", "Kangaroo", "Karate", "Kites", "Koalas",
            "Leadership", "Love", "Literature", "Lakes", "Laughter",
            "Mountain", "Music", "Mathematics", "Mystery", "Museums",
            "Nature", "Networking", "Nanotechnology", "Nebula", "Nutrition",
            "Oceans", "Outdoor", "Origami", "Optimism", "Orchestra",
            "Painting", "Photography", "Programming", "Peace", "Philosophy",
            "Quizzes", "Quality", "Quest", "Quasars", "Quiet",
            "Reading", "Robotics", "Research", "Rainbows",
            "Sailing", "Science", "Soccer", "Space", "Stars",
            "Technology", "Travel", "Teamwork", "Tigers", "Theater",
            "Umbrella", "Unity", "Underwater", "Universe", "Ultimate",
            "Vacation", "Volunteer", "Video Games", "Values", "Victory",
            "Wellness", "Waterfalls", "Wildlife", "Wonders", "Waves",
            "Xylophone", "X-ray", "Xenophobia", "Xenon", "Xerophyte",
            "Yoga", "Youth", "Yacht", "Yearning", "Yesteryear",
            "Zebra", "Zoo", "Zen", "Zest", "Zero"
        )
        val filterWords = wordsList.filter { it.startsWith(key, ignoreCase = true) }
        return filterWords.map { WordsModel(it) }
    }
}