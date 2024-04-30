package com.tutorials.eu.favdish.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tutorials.eu.favdish.R
import com.tutorials.eu.favdish.application.FavDishApplication
import com.tutorials.eu.favdish.databinding.FragmentAllDishesBinding
import com.tutorials.eu.favdish.model.entities.FavDish
import com.tutorials.eu.favdish.view.activities.AddUpdateDishActivity
import com.tutorials.eu.favdish.view.activities.MainActivity
import com.tutorials.eu.favdish.view.adapters.FavDishAdapter
import com.tutorials.eu.favdish.viewmodel.FavDishViewModel
import com.tutorials.eu.favdish.viewmodel.FavDishViewModelFactory

class AllDishesFragment : Fragment() {

    private lateinit var mBinding: FragmentAllDishesBinding

    private val mFavDishViewModel: FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvDishesList.layoutManager = GridLayoutManager(requireActivity(), 2)
        val favDishAdapter = FavDishAdapter(this@AllDishesFragment)

        mBinding.rvDishesList.adapter = favDishAdapter

        mFavDishViewModel.allDishesList.observe(viewLifecycleOwner) { dishes ->
            dishes.let {
//                for (item in it) {
//                    Log.i("Dish Title", "${item.id} :: ${item.title}")
//                }
                if (it.isNotEmpty()) {
                    mBinding.rvDishesList.visibility = View.VISIBLE
                    mBinding.tvNoDishesAddedYet.visibility = View.GONE
                    favDishAdapter.dishesList(it)
                } else {
                    mBinding.rvDishesList.visibility = View.GONE
                    mBinding.tvNoDishesAddedYet.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAllDishesBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    fun dishDetails(favDish: FavDish) {
        findNavController().navigate(
            AllDishesFragmentDirections.actionAllDishesToDishDetails(
                favDish
            )
        )

        if (requireActivity() is MainActivity) {
            (activity as MainActivity?)?.hideBottomNavigationView()
        }
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity) {
            (activity as MainActivity?)?.showBottomNavigationView()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_dishes, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_add_dish -> {
                startActivity(Intent(requireActivity(), AddUpdateDishActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}