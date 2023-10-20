package id.anantyan.challengechapter3.domain.base

import id.anantyan.challengechapter3.data.alphabet.AlphabetModel
import id.anantyan.challengechapter3.data.words.WordsModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class BaseRepositoryImpl: BaseRepository {
    override suspend fun listAlphabet(): List<AlphabetModel> = coroutineScope {
        val alphabets = ('A'..'Z')
        val items = alphabets.map { letter ->
            async {
                val wordsForLetter = listWord(letter.toString()).take(3)
                AlphabetModel(letter.toString(), wordsForLetter)
            }
        }
        items.awaitAll()
    }

    override fun listWord(key: String): List<WordsModel> {
        val wordsList = listOf(
            "Apple", "Art", "Adventure", "Algorithm", "Airplane", "Aquarium", "Architecture", "Astronomy", "Athletics", "Automobile",
            "Basketball", "Beach", "Biology", "Ballet", "Books", "Baking", "Business", "Boxing", "Botany", "Building",
            "Coding", "Cooking", "Climbing", "Chemistry", "Cinema", "Chess", "Cycling", "Computers", "Creativity", "Crafts",
            "Dance", "Design", "Drawing", "Drama", "Diving", "Dinosaurs", "Data", "Desserts", "Discovery", "Drones",
            "Education", "Engineering", "Environment", "Exploration", "Economics", "Equestrian", "Ecology", "Einstein", "Electronics", "Energy",
            "Fitness", "Football", "Fishing", "Fashion", "Film", "Food", "Fiction", "Flowers", "Friends", "Future",
            "Gardening", "Guitar", "Games", "Geography", "Genetics", "Golf", "Global", "Gravity", "Gymnastics", "Galaxy",
            "Health", "History", "Hiking", "Hockey", "Horror", "Holidays", "Happiness", "Horses", "Humanity", "Harmony",
            "Ice Cream", "Internet", "Innovation", "Imagination", "Identity", "Insects", "Inspiration", "Ivy", "Islands", "Intelligence",
            "Jazz", "Journey", "Justice", "Jigsaw", "Juggling", "Jungle", "Joy", "Journalism", "Jewelry", "Judgment",
            "Knowledge", "Kangaroo", "Karate", "Kites", "Koalas", "Kindness", "Kaleidoscope", "Karma", "Kung Fu", "Kingdom",
            "Leadership", "Love", "Literature", "Lakes", "Laughter", "Languages", "Lighthouses", "Lions", "Logic", "Liberty",
            "Mountain", "Music", "Mathematics", "Mystery", "Museums", "Monkeys", "Mountains", "Meditation", "Meteorology", "Microbiology",
            "Nature", "Networking", "Nanotechnology", "Nebula", "Nutrition", "Neuroscience", "Noble", "Nomads", "Nurturing", "Numbers",
            "Oceans", "Outdoor", "Origami", "Optimism", "Orchestra", "Observation", "Oceanography", "Opportunity", "Owls", "Organic",
            "Painting", "Photography", "Programming", "Peace", "Philosophy", "Planets", "Parks", "Puzzles", "Penguins", "Perseverance",
            "Quizzes", "Quality", "Quest", "Quasars", "Quiet", "Quokkas", "Quantum", "Questions", "Quench", "Quick",
            "Reading", "Robotics", "Research", "Rainbows", "Rivers", "Romance", "Riddles", "Respect", "Radiance", "Revolution",
            "Sailing", "Science", "Soccer", "Space", "Stars", "Sunsets", "Symphony", "Surfing", "Spirituality", "Sustainability",
            "Technology", "Travel", "Teamwork", "Tigers", "Theater", "Teaching", "Tropical", "Telescopes", "Tranquility", "Transformation",
            "Umbrella", "Unity", "Underwater", "Universe", "Ultimate", "Understanding", "Upbeat", "Uplifting", "Urban", "Usability",
            "Vacation", "Volunteer", "Video Games", "Values", "Victory", "Vitality", "Vision", "Voyage", "Validation", "Voyager",
            "Wellness", "Waterfalls", "Wildlife", "Wonders", "Waves", "Wisdom", "Whales", "Wealth", "Wonderland", "Winning",
            "Xylophone", "X-ray", "Xenophobia", "Xenon", "Xerophyte", "Xenophile", "X-axis", "Xenocryst", "Xylograph", "Xylography",
            "Yoga", "Youth", "Yacht", "Yearning", "Yesteryear", "Yummy", "Yellow", "Yield", "Yarn", "Yoke",
            "Zebra", "Zoo", "Zen", "Zest", "Zero", "Zigzag", "Zinc", "Zodiac", "Zone", "Zoom"
        )
        val filterWords = wordsList.filter { it.startsWith(key, ignoreCase = true) }
        return filterWords.map { WordsModel(it) }
    }
}