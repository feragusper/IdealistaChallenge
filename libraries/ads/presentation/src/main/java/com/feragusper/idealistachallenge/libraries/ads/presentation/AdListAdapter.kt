package com.feragusper.idealistachallenge.libraries.ads.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.feragusper.idealistachallenge.libraries.ads.presentation.databinding.ItemAdBinding
import com.feragusper.idealistachallenge.libraries.design.R

/**
 * Adapter for displaying a list of ads in a RecyclerView.
 *
 * @param ads The list of ads to be displayed.
 * @param onClick Callback for handling item click events.
 * @param onFavoriteClick Callback for handling favorite button click events.
 */
class AdListAdapter(
    private var ads: List<AdSummaryUi>,
    private val onClick: () -> Unit,
    private val onFavoriteClick: (String) -> Unit
) : RecyclerView.Adapter<AdListAdapter.AdViewHolder>() {

    /**
     * ViewHolder for displaying an ad item.
     *
     * @param binding The view binding for the item layout.
     */
    inner class AdViewHolder(private val binding: ItemAdBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds the ad data to the view.
         *
         * @param ad The ad data to be displayed.
         */
        fun bind(ad: AdSummaryUi) {
            with(binding) {
                with(itemView.context) {
                    textTitle.text = ad.title.format(this)
                    textSubtitle.text = ad.subtitle.format(this)
                    textPrice.text = ad.price
                    textRooms.text = ad.rooms.format(this)
                    textSize.text = ad.size.format(this)
                    extra.text = ad.extra.joinToString(" - ") {
                        it.format(this)
                    }
                    imageCarousel.images = ad.images

                    btnFavorite.setImageResource(
                        if (ad.isFavorite) {
                            R.drawable.ic_favorite
                        } else {
                            R.drawable.ic_favorite_border
                        }
                    )
                    chipOperationType.text = ad.operationType.format(this)
                    btnFavorite.setOnClickListener { onFavoriteClick(ad.id) }
                }
            }

            itemView.setOnClickListener { onClick() }
        }

    }

    /**
     * Creates a new ViewHolder for the ad item.
     *
     * @param parent The parent view group.
     * @param viewType The view type.
     *
     * @return The created ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val binding = ItemAdBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AdViewHolder(binding)
    }

    /**
     * Binds the data to the ViewHolder.
     *
     * @param holder The ViewHolder to bind the data to.
     * @param position The position of the item in the list.
     */
    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        holder.bind(ads[position])
    }

    /**
     * Returns the number of items in the list.
     *
     * @return The number of items in the list.
     */
    override fun getItemCount(): Int = ads.size

    /**
     * Updates the list of ads and notifies the adapter of the data change.
     *
     * @param newAds The new list of ads to be displayed.
     */
    fun updateAds(newAds: List<AdSummaryUi>) {
        val diffCallback = AdsDiffCallback(ads, newAds)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        ads = newAds
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Callback for calculating the difference between two lists of ads.
     *
     * @param oldList The old list of ads.
     * @param newList The new list of ads.
     */
    class AdsDiffCallback(
        private val oldList: List<AdSummaryUi>,
        private val newList: List<AdSummaryUi>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
