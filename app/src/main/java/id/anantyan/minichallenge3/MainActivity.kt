package id.anantyan.minichallenge3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.minichallenge3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainInteraction {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    private fun bindView() {
        adapter = MainAdapter()

        binding.rvList.setHasFixedSize(true)
        binding.rvList.itemAnimator = DefaultItemAnimator()
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.submitList(dataDummy())
        adapter.onInteraction(this)
    }

    private fun dataDummy(): List<MainAdapterModel> {
        return listOf(
            MainAdapterModel(
                title = "Intent Extras",
                description = "Passing data dari Activity A ke Activity B menggunakan Intent Extras",
                mainModel = MainModel(
                    id = 1, name = "Arya", cumlaude = false
                )
            ),
            MainAdapterModel(
                title = "Intent Bundle",
                description = "Passing data dari Activity A ke Activity B menggunakan Intent Bundle",
                mainModel = MainModel(
                    id = 2, name = "Rezza", cumlaude = true
                )
            ),
            MainAdapterModel(
                title = "Intent Serialize",
                description = "Passing data dari Activity A ke Activity B menggunakan Intent Serialize",
                mainModel = MainModel(
                    id = 3, name = "Anantya", cumlaude = false
                )
            )
        )
    }

    override fun onClick(position: Int, item: MainAdapterModel) {
        when (position) {
            0 -> {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("title", item.title)
                intent.putExtra("id", item.mainModel.id)
                intent.putExtra("name", item.mainModel.name)
                intent.putExtra("cumlaude", item.mainModel.cumlaude)
                startActivity(intent)
            }
            1 -> {
                val bundle = Bundle()
                bundle.putString("title", item.title)
                bundle.putInt("id", item.mainModel.id ?: 0)
                bundle.putString("name", item.mainModel.name)
                bundle.putBoolean("cumlaude", item.mainModel.cumlaude ?: false)

                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            2 -> {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("title", item.title)
                intent.putExtra("parcelable", item.mainModel)
                startActivity(intent)
            }
        }
    }
}