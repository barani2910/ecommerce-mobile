package com.example.ecommerce.data.remote

import com.example.ecommerce.data.model.Product
import retrofit2.http.*

interface ApiService {
    // GET: Fetch all products
    @GET("products")
    suspend fun getProducts(): List<Product>

    // GET: Fetch a single product by ID
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: String): Product

    // POST: Create a new product
    @POST("products")
    suspend fun createProduct(@Body product: Product): Product

    // PUT: Update an existing product
    @PUT("products/{id}")
    suspend fun updateProduct(@Path("id") id: String, @Body product: Product): Product

    // DELETE: Delete a product
    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Product
}
