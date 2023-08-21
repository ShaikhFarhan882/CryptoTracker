package com.example.cryptotracker.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.MainActivity
import com.example.cryptotracker.R
import com.example.cryptotracker.adapter.CryptoAdapter
import com.example.cryptotracker.databinding.FragmentFavouritesBinding
import com.example.cryptotracker.utils.EmptyDataObserver
import com.example.cryptotracker.viewmodel.CryptoViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.LandingAnimator

@AndroidEntryPoint
class Favourites : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding: FragmentFavouritesBinding get() = _binding!!

    private val viewModel by viewModels<CryptoViewModel>()

    lateinit var favAdapter: CryptoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavouritesBinding.inflate(layoutInflater)


        favAdapter = CryptoAdapter()
        setRecyclerView(favAdapter)

        viewModel.getSavedCrypto().observe(viewLifecycleOwner, Observer {
            favAdapter.submitList(it)

        })


        //swipe to delete
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val crypto = favAdapter.currentList[position]
                viewModel.delete(crypto)
                val view = binding.root
                Snackbar.make(view, "Deleted Successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.upsert(crypto)
                    }
                    show()
                }

            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.FavRecView)
        }

        setHasOptionsMenu(true)

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fav_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteALL -> {
                viewModel.deleteALl()
                deleteAllDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setRecyclerView(myAdapter: CryptoAdapter) {
        binding.FavRecView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myAdapter

            val emptyDataObserver = EmptyDataObserver(binding.FavRecView, binding.emptyStateRecview)
            favAdapter.registerAdapterDataObserver(emptyDataObserver)

            itemAnimator = LandingAnimator().apply {
                addDuration = 400L
            }
        }
    }

    private fun deleteAllDialog() {
        val alertDialog = AlertDialog.Builder(requireContext()).apply {
            setTitle("Clear All")
            setMessage("Delete all saved currencies?")
            setIcon(R.drawable.ic_delete)
            setPositiveButton("Yes") { i, j ->
                viewModel.deleteALl()
                Toast.makeText(requireContext(), "Cleared All", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("No") { i, j ->

            }
            setCancelable(true)
        }.create().show()
    }


}