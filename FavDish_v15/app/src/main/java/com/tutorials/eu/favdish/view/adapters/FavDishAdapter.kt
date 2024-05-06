package com.tutorials.eu.favdish.view.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tutorials.eu.favdish.R
import com.tutorials.eu.favdish.databinding.ItemDishLayoutBinding
import com.tutorials.eu.favdish.model.entities.FavDish
import com.tutorials.eu.favdish.utils.Constants
import com.tutorials.eu.favdish.view.activities.AddUpdateDishActivity
import com.tutorials.eu.favdish.view.fragments.AllDishesFragment
import com.tutorials.eu.favdish.view.fragments.FavouriteDishesFragment

class FavDishAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<FavDishAdapter.ViewHolder>() {

    private var dishes: List<FavDish> = listOf()


    class ViewHolder(view: ItemDishLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        val ivDishImage = view.ivDishImage
        val tvTitle = view.tvDishTitle
        val ibMore = view.ibMore
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding: ItemDishLayoutBinding = ItemDishLayoutBinding.inflate(
            LayoutInflater.from(fragment.context), p0, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val dish = dishes[p1]
        Glide.with(fragment)
            .load(dish.image)
            .into(p0.ivDishImage)
        p0.tvTitle.text = dish.title

        p0.itemView.setOnClickListener{
            if(fragment is AllDishesFragment){
                fragment.dishDetails(dish)
            }
            if(fragment is FavouriteDishesFragment){
                fragment.dishDetails(dish)
            }
        }

        p0.ibMore.setOnClickListener{
            val popup = PopupMenu(fragment.context, p0.ibMore)
            popup.menuInflater.inflate(R.menu.menu_adapter, popup.menu)

            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_edit_dish){
                    val intent = Intent(fragment.requireActivity(), AddUpdateDishActivity::class.java)
                    intent.putExtra(Constants.EXTRA_DISH_DETAILS, dish)
                    fragment.requireActivity().startActivity(intent)

                } else if(it.itemId == R.id.action_delete_dish){
                    if(fragment is AllDishesFragment){
                        fragment.deleteDish(dish)
                    }
                }
                true
            }
            popup.show()
        }

        if(fragment is AllDishesFragment){
            p0.ibMore.visibility = View.VISIBLE
        } else if(fragment is FavouriteDishesFragment){
            p0.ibMore.visibility = View.GONE
        }
    }

    fun dishesList(list: List<FavDish>) {
        dishes = list
        notifyDataSetChanged()
    }
}