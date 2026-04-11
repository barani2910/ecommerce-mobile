package com.example.ecommerce.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.data.repository.CartRepository
import com.example.ecommerce.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeCart()

        binding.btnCheckout.setOnClickListener {
            if ((CartRepository.cartItems.value?.size ?: 0) > 0) {
                findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(
            onIncrease = { CartRepository.updateQuantity(it.product.id, it.quantity + 1) },
            onDecrease = { CartRepository.updateQuantity(it.product.id, it.quantity - 1) },
            onRemove = { CartRepository.removeFromCart(it.product.id) }
        )
        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CartFragment.adapter
        }
    }

    private fun observeCart() {
        CartRepository.cartItems.observe(viewLifecycleOwner) { items ->
            Log.d("CartFragment", "Cart items changed: ${items.size}")
            adapter.submitList(items)
            binding.tvTotalPrice.text = "$${String.format("%.2f", CartRepository.getTotalPrice())}"
            binding.btnCheckout.isEnabled = items.isNotEmpty()
            
            // Show/hide empty state (optional but recommended)
            if (items.isEmpty()) {
                // You can add an empty view in XML and show it here
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
