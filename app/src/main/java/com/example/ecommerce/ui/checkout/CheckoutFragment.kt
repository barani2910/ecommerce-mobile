package com.example.ecommerce.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.data.repository.CartRepository
import com.example.ecommerce.databinding.FragmentCheckoutBinding
import com.example.ecommerce.ui.cart.CartAdapter

class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckoutViewModel by viewModels()
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        binding.tvTotalAmount.text = "$${String.format("%.2f", CartRepository.getTotalPrice())}"

        binding.btnPlaceOrder.setOnClickListener {
            val address = binding.etAddress.text.toString()
            viewModel.placeOrder(address)
        }
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(
            onIncrease = {},
            onDecrease = {},
            onRemove = {}
        )
        binding.rvCheckoutItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CheckoutFragment.adapter
        }
        CartRepository.cartItems.value?.let {
            adapter.submitList(it)
        }
    }

    private fun observeViewModel() {
        viewModel.orderPlaced.observe(viewLifecycleOwner) { placed ->
            if (placed) {
                Toast.makeText(context, "Order placed successfully!", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_checkoutFragment_to_homeFragment)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnPlaceOrder.isEnabled = !isLoading
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
