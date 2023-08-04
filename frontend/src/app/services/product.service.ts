import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';
import { Category } from '../models/category.models';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient) { }

  addProduct(product: Product) {
    return this.httpClient.post("http://localhost:8080/products", product);
  }

  deleteProduct() {

  }

  getProducts() {
    
  }

  getProduct(productId: number) {
    return this.httpClient.get<Product>("http://localhost:8080/products/" + productId);
  }

  editProduct(product: Product) {
    return this.httpClient.put("http://localhost:8080/products/" + product.id, product);
  }
}
