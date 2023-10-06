package id.anantyan.minichallenge3

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.minichallenge3.common.parcelable
import id.anantyan.minichallenge3.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var adapter: SecondAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    private fun bindView() {
        adapter = SecondAdapter()

        binding.rvList.setHasFixedSize(true)
        binding.rvList.itemAnimator = DefaultItemAnimator()
        binding.rvList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvList.adapter = adapter

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        bindData()
    }

    private fun bindData() {
        when (intent.getIntExtra("position", 0)) {
            0 -> {
                val title = intent.getStringExtra("title")
                val id = intent.getIntExtra("id", 0).toString()
                val name = intent.getStringExtra("name")
                val cumlaude = intent.getBooleanExtra("cumlaude", false).toString()

                binding.txtTitle.text = title
                adapter.submitList(listOf(
                    "ID: $id", "Name: $name", "Cumlaude: $cumlaude"
                ))
            }
            1 -> {
                intent.extras?.let {
                    val title = it.getString("title")
                    val id = it.getInt("id").toString()
                    val name = it.getString("name")
                    val cumlaude = it.getBoolean("cumlaude").toString()

                    binding.txtTitle.text = title
                    adapter.submitList(listOf(
                        "ID: $id", "Name: $name", "Cumlaude: $cumlaude"
                    ))
                }
            }
            2 -> {
                val title = intent.getStringExtra("title")
                intent.parcelable<MainModel>("parcelable")?.let {
                    binding.txtTitle.text = title
                    adapter.submitList(listOf(
                        "ID: ${it.id}", "Name: ${it.name}", "Cumlaude: ${it.cumlaude}"
                    ))
                }
            }
        }
    }
}