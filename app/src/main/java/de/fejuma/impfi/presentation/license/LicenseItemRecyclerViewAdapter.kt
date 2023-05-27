package de.fejuma.impfi.presentation.license

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.fejuma.impfi.R

import de.fejuma.impfi.presentation.license.placeholder.PlaceholderContent.PlaceholderItem
import de.fejuma.impfi.databinding.ItemLicenseBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class LicenseItemRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<LicenseItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemLicenseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
//        holder.idView.text = item.id
        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemLicenseBinding) : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.itemContent

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}