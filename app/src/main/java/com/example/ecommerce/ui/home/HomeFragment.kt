package com.example.ecommerce.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentHomeBinding
import com.example.ecommerce.ui.auth.AuthViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMenu()
        setupRecyclerView()
        setupCategories()
        observeViewModel()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_cart -> {
                        findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
                        true
                    }
                    R.id.action_orders -> {
                        findNavController().navigate(R.id.action_homeFragment_to_ordersFragment)
                        true
                    }
                    R.id.action_logout -> {
                        authViewModel.logout()
                        findNavController().navigate(R.id.loginFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter { product ->
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product)
            findNavController().navigate(action)
        }
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@HomeFragment.adapter
        }
    }

    private fun setupCategories() {
        binding.chipGroupCategories.setOnCheckedStateChangeListener { group, checkedIds ->
            when (checkedIds.firstOrNull()) {
                R.id.chipAll -> viewModel.fetchProducts()
                R.id.chipElectronics -> viewModel.filterByCategory("Electronics")
                R.id.chipClothing -> viewModel.filterByCategory("Clothing")
            }
        }
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
