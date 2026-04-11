package com.example.ecommerce.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.ecommerce.data.model.Product
import com.example.ecommerce.data.repository.CartRepository
import com.example.ecommerce.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product
        setupProductDetails(product)

        binding.btnAddToCart.setOnClickListener {
            CartRepository.addToCart(product)
            Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupProductDetails(product: Product) {
        binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "$${product.price}"
            tvProductDescription.text = product.description
            Glide.with(ivProduct.context)
                .load(product.imageUrl)
                .into(ivProduct)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
