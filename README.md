# SynrgyChapter3

### 🚩Table of Contents
[Description](https://github.com/anantyan/SynrgyChapter3#-description)<br>
[Project Structure](https://github.com/anantyan/SynrgyChapter3#-project-structure)<br>
[Snapshoot](https://github.com/anantyan/SynrgyChapter3#%EF%B8%8F-snapshoot)<br>
[Capture Video](https://github.com/anantyan/SynrgyChapter3#-capture-video)

### 📌 Description
Hasil challenge dari pembelajaran intensif chapter 3 - Yang dimana project tersebut berisikan sebuah konten list huruf abjad a-z ketika pengguna klik maka beralih ke list kata kunci sesuai huruf yang pengguna klik, setelah itu akan terdapat list kata kunci yang bisa di klik mengarah ke pencarian Google

***note:*** Menekankan pada kriteria task dan paling utama telah mengimplementasikan non navigation component (Intent Implicit & Explicit), telah dibuatkan dengan cara 2 interaksi klik, klik yang pertama (tekan biasa pada list item **Halaman Home Detail**) akan mengarah pada Web View Activity dan menggunakan ***Web In App*** ✔ klik yang kedua (tekan tahan pada list item **Halaman Home Detail**) mengarah pada Web Implicit tanpa menggunakan ***Web In App*** ✔

- Implement **Intent Implicit** - [link](https://github.com/anantyan/SynrgyChapter3/blob/d0e143b63d25ccc16c43280a8d5132e9caf0d998/app/src/main/java/id/anantyan/challengechapter3/ui/detail/DetailActivity.kt#L90-L94)
```kotlin
override fun onClick(position: Int, item: WordsModel, view: View) {
		val intent = Intent(Intent.ACTION_VIEW)
		intent.data = Uri.parse(getString(R.string.intent_to_weburl)+item.word)
		startActivity(intent)
}
```

- Implement **Intent Explicit** - [link](https://github.com/anantyan/SynrgyChapter3/blob/d0e143b63d25ccc16c43280a8d5132e9caf0d998/app/src/main/java/id/anantyan/challengechapter3/ui/detail/DetailActivity.kt#L96-L104)
```kotlin
override fun onLongClick(position: Int, item: WordsModel, view: View) {
    val extras = ActivityOptionsCompat.makeSceneTransitionAnimation(
        this,
        view,
        view.transitionName
    )
    val intent = GoogleActivity.getIntent(this, item.word)
    startActivity(intent, extras.toBundle())
}
```

### 👣 Project Structure
- app
    - src
        - main
            - common
            - model
            - repository
            - ui
                - detail
                - google
                - home
                - onboarding

### 🖼️ Snapshoot
![Desktop - 1](https://github.com/anantyan/SynrgyChapter3/assets/43742778/42a6f601-20f9-44f6-a587-eed8ec7a81f7)
![Desktop - 3](https://github.com/anantyan/SynrgyChapter3/assets/43742778/b1bcb272-1fca-4379-ab01-80e5ef432bad)

### 📹 Capture Video
https://github.com/anantyan/SynrgyChapter3/assets/43742778/0cfc82b9-cf06-43d1-9e11-8ee40544a986
