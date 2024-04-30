package com.tutorials.eu.favdish.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tutorials.eu.favdish.application.FavDishApplication
import com.tutorials.eu.favdish.databinding.FragmentFavouriteDishesBinding
import com.tutorials.eu.favdish.model.entities.FavDish
import com.tutorials.eu.favdish.view.activities.MainActivity
import com.tutorials.eu.favdish.view.adapters.FavDishAdapter
import com.tutorials.eu.favdish.viewmodel.FavDishViewModel
import com.tutorials.eu.favdish.viewmodel.FavDishViewModelFactory

class FavouriteDishesFragment : Fragment() {

    private var mBinding: FragmentFavouriteDishesBinding? = null

    private val mFavDishViewModel: FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFavouriteDishesBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFavDishViewModel.favouriteDishes.observe(viewLifecycleOwner) { dishes ->
            dishes.let {

                mBinding!!.rvFavouriteDishesList.layoutManager =
                    GridLayoutManager(requireActivity(), 2)
                val adapter = FavDishAdapter(this@FavouriteDishesFragment)
                mBinding!!.rvFavouriteDishesList.adapter = adapter

                if (it.isNotEmpty()) {
                    mBinding!!.rvFavouriteDishesList.visibility = View.VISIBLE
                    mBinding!!.tvNoFavouriteAddedYet.visibility = View.GONE
                    adapter.dishesList(it)
                } else {
                    mBinding!!.rvFavouriteDishesList.visibility = View.GONE
                    mBinding!!.tvNoFavouriteAddedYet.visibility = View.VISIBLE
                    Log.i("List of Favourite Dishes", "is Empty.")
                }
            }
        }
    }

    fun dishDetails(favDish: FavDish) {
        findNavController().navigate(
            FavouriteDishesFragmentDirections
                .actionFavouriteDishesToDishDetails(favDish)
        )
        if (requireActivity() is MainActivity) {
            (activity as MainActivity?)!!.hideBottomNavigationView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity){
            (activity as MainActivity?)!!.showBottomNavigationView()
        }
    }
}