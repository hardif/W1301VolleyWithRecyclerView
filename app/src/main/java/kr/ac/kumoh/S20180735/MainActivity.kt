package kr.ac.kumoh.S20180735

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kumoh.S20180735.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: SongViewModel
    private val songAdapter = SongAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[SongViewModel::class.java]

        binding.list.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = songAdapter
        }

        model.list.observe((this)) {
            songAdapter.notifyItemRangeInserted(0,model.list.value?.size ?: 0)
        }
        model.requestSong()
    }
    inner class SongAdapter : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txTitle = itemView.findViewById<TextView>(android.R.id.text1)
            val txSinger = itemView.findViewById<TextView>(android.R.id.text2)
        }

        override fun onCreateViewHolder(parent: ViewGroup,viewType: Int) : ViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_expandable_list_item_2, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount() = model.list?.value?.size ?: 0

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.txTitle.text = model.list?.value?.get(position)?.title ?: null
            holder.txSinger.text = model.list?.value?.get(position)?.singer ?: null
        }
    }
}