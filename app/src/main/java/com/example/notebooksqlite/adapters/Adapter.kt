package com.example.notebooksqlite.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.notebooksqlite.interfaces.OnRecyclerViewItemClickListener
import com.example.notebooksqlite.R
import com.example.notebooksqlite.databinding.NoteItemBinding
import com.example.notebooksqlite.models.Model


class Adapter(private val mainItemList: ArrayList<Model>,
              listener: OnRecyclerViewItemClickListener
): RecyclerView.Adapter<Adapter.ViewHolder>() {
    private var itemList = mainItemList
    private val adapterListener = listener


    class ViewHolder(itemView: View, listener: OnRecyclerViewItemClickListener):
        RecyclerView.ViewHolder(itemView){
        private val binding = NoteItemBinding.bind(itemView)
        private val holderListener = listener
        fun init(item: Model) = with(binding) {
            itemTitle.text = item.title
            item.edit = true
            if (item.uri != "empty"){
                itemImgView.visibility = View.VISIBLE
                itemImgView.load(Uri.parse(item.uri)){
                    crossfade(true)
                    transformations(RoundedCornersTransformation(
                        topLeft = 5f,
                        topRight = 5f,
                        bottomLeft = 5f,
                        bottomRight = 5f
                    ))
                }
            }
            itemView.setOnClickListener {
                holderListener.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.note_item, viewGroup, false)
        return ViewHolder(view, adapterListener)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.init(itemList[position])

    }
    override fun getItemCount() = itemList.size
    fun updateAdapter(mainItemList: ArrayList<Model>){
        itemList.clear()
        itemList.addAll(mainItemList)
        notifyDataSetChanged()
    }
}
