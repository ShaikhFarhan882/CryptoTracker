package com.example.cryptotracker.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.cryptotracker.MainActivity
import com.example.cryptotracker.R
import com.example.cryptotracker.adapter.CryptoAdapter
import com.example.cryptotracker.databinding.FragmentHomeBinding
import com.example.cryptotracker.utils.Resource
import com.example.cryptotracker.viewmodel.CryptoViewModel
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator


class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private lateinit var viewModel: CryptoViewModel
    lateinit var myAdapter: CryptoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel = (activity as MainActivity).viewModel

       /* (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbarHome)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "All Cryptocurrencies"*/

       /* (requireActivity() as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(getResources().getColor(R.color.purple_500)));*/




        myAdapter = CryptoAdapter()
        setRecyclerView(myAdapter)


        viewModel.cryptoList.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideOfflineLogo()
                  response.data?.let{
                      myAdapter.submitList(it!!.data.toList())
                  }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity,"Error : ${it.toString()}", Toast.LENGTH_SHORT)
                            .show()
                        showOfflineLogo()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                    hideOfflineLogo()
                }

            }
            /* myAdapter.submitList(it.data.toList())*/
        })

        myAdapter.setOnItemClickListener {
            viewModel.upsert(it)
            Toast.makeText(requireContext(), "Saved to Favourites", Toast.LENGTH_SHORT).show()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                viewModel.getData()
                Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setRecyclerView(myAdapter: CryptoAdapter) {
        binding.RecView.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = SlideInDownAnimator().apply {
                addDuration = 400L
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showProgressBar() {
      binding.ProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.ProgressBar.visibility = View.INVISIBLE
    }

    private fun hideOfflineLogo() {
        binding.offlineLogo.visibility = View.INVISIBLE
        binding.RecView.visibility = View.VISIBLE
    }

    private fun showOfflineLogo() {
        binding.offlineLogo.visibility = View.VISIBLE
        binding.RecView.visibility = View.INVISIBLE
    }


}