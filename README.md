# SynrgyChapter3

### 🚩Table of Contents
[Description](https://github.com/anantyan/SynrgyChapter3#-description)<br>
[Project Structure](https://github.com/anantyan/SynrgyChapter3#-project-structure)<br>
[Snapshoot](https://github.com/anantyan/SynrgyChapter3#%EF%B8%8F-snapshoot)<br>
[Capture Video](https://github.com/anantyan/SynrgyChapter3#-capture-video)

### 📌 Description
Hasil challenge dari pembelajaran intensif chapter 3 - Yang dimana project tersebut berisikan sebuah konten list huruf abjad a-z ketika pengguna klik maka beralih ke list kata kunci sesuai huruf yang pengguna klik, setelah itu akan terdapat list kata kunci yang bisa di klik mengarah ke pencarian Google

***note:*** Menekankan pada kriteria task dan paling utama telah mengimplementasikan 2 navigasi, telah dibuatkan dengan cara 2 interaksi klik, klik yang pertama (tekan biasa pada list item **Halaman Home**) akan mengarah pada detail fragment dan menggunakan ***Navigation Component*** ✔ klik yang kedua (tekan tahan pada list item **Halaman Home**) mengarah pada detail activity tanpa menggunakan ***Navigation Component*** ✔ dan yang terakhir telah mengimplementasikan _**Intent Implicit**_ ke web url ✔

- **Implement onClick to other Fragment (Detail Fragment) - [link](https://github.com/anantyan/SynrgyChapter3/blob/e85303b03143eea425732d6ac15a33b153385d0a/app/src/main/java/id/anantyan/challengechapter3/ui/home/HomeFragment.kt#L101-L105)**
```kotlin
override fun onClick(position: Int, item: AlphabetModel, view: View) {
    val extras = FragmentNavigatorExtras(view to (item.key ?: ""))
    val destination = HomeFragmentDirections.actionHomeFragmentToDetailFragment(item.key)
    findNavController().navigate(destination, extras)
}
```

- **Implement onClick to other Activity (Detail Activity) - [link](https://github.com/anantyan/SynrgyChapter3/blob/e85303b03143eea425732d6ac15a33b153385d0a/app/src/main/java/id/anantyan/challengechapter3/ui/home/HomeFragment.kt#L107-L110)**
```kotlin
override fun onLongClick(position: Int, item: AlphabetModel, view: View) {
    val intent = DetailActivity.getIntent(requireContext(), item.key)
    startActivity(intent)
}
```

- **Implement get passing data from other Fragment (Home Fragment) to Detail Fragment - [link](https://github.com/anantyan/SynrgyChapter3/blob/e85303b03143eea425732d6ac15a33b153385d0a/app/src/main/java/id/anantyan/challengechapter3/ui/detail/DetailFragment.kt#L57-L78)**
```kotlin
// initialize safe args
private val args: DetailFragmentArgs by navArgs()

// get data from other fragment
private fun bindObserver() {
    viewModel.getAll(args.key, false)

    lifecycleScope.launch {
        viewModel.getAll.collect { list ->
            adapter.submitList(list)
        }
    }

    lifecycleScope.launch {
        viewModel.toggleGrid.collect {
            val spanCount = if (it) 1 else 2
            binding.rvList.layoutManager = GridLayoutManager(requireContext(), spanCount)
        }
    }

    lifecycleScope.launch {
        viewModel.toggleSort.collect {
            viewModel.getAll(args.key, it)
        }
    }
}
```

- **Implement get passing data from other Fragment (Home Fragment) to Detail Activity - [link](https://github.com/anantyan/SynrgyChapter3/blob/e85303b03143eea425732d6ac15a33b153385d0a/app/src/main/java/id/anantyan/challengechapter3/ui/detail/DetailActivity.kt#L40-L61)**
```kotlin
// singleton intent with passing data
companion object {
    private const val EXTRA_HOME_DETAIL = "EXTRA_DETAIL_ACTIVIVTY"

    @JvmStatic
    fun getIntent(context: Context?, key: String?) = Intent(context, DetailActivity::class.java).apply {
        key?.let {
            putExtra(EXTRA_HOME_DETAIL, it)
        }
    }
}

// initilize extras
private val key: String by lazy { intent.getStringExtra(EXTRA_HOME_DETAIL) ?: "" }

// get data from other fragment
private fun bindObserver() {
    viewModel.getAll(key, false)

    lifecycleScope.launch {
        viewModel.getAll.collect { list ->
            adapter.submitList(list)
        }
    }

    lifecycleScope.launch {
        viewModel.toggleGrid.collect {
            val spanCount = if (it) 1 else 2
            binding.rvList.layoutManager = GridLayoutManager(this@DetailActivity, spanCount)
        }
    }

    lifecycleScope.launch {
        viewModel.toggleSort.collect {
            viewModel.getAll(key, it)
        }
    }
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
                - base
                - detail
                - google
                - home
                - onboarding_1
                - onboarding_2

### 🖼️ Snapshoot
![Desktop - 1](https://github.com/anantyan/SynrgyChapter3/assets/43742778/42a6f601-20f9-44f6-a587-eed8ec7a81f7)
![Desktop - 2](https://github.com/anantyan/SynrgyChapter3/assets/43742778/02b406a0-dbbe-424f-ad97-47bd619c885d)

### 📹 Capture Video
https://github.com/anantyan/SynrgyChapter3/assets/43742778/1475809c-d0fa-4d0e-aec9-9c779d333ee8
