import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';
import { Category } from '../models/category.models';
import { environment } from '../../environment/environment';
import { Page } from '../models/page.interface';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private url = environment.baseUrl + "/products/";
  private productsPerPage = 2;

  constructor(private httpClient: HttpClient) { }

  addProduct(product: Product) {
    return this.httpClient.post(environment.baseUrl + "/products", product);
  }

  deleteProduct(product: Product) {
    return this.httpClient.delete<Product[]>(this.url + product.id);
  }

  getProducts() {
    const options = this.getAuthToken();

    return this.httpClient.get<Product[]>(environment.baseUrl + "/products", options);
  }

  private getAuthToken() {
    return {
      headers: new HttpHeaders({
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      })
    };
  }

  getPublicProducts(currentPage: number) {
    return this.httpClient
    .get<Page>(environment.baseUrl + `/public-products?page=${currentPage}&size=${this.productsPerPage}`);
  }

  getProduct(productId: number) {
    return this.httpClient.get<Product>(this.url + productId);
  }

  editProduct(product: Product) {
    return this.httpClient.put<void>(this.url + product.id, product);
  }

  decreaseStock(product: Product) {
    return this.httpClient.patch<Product[]>(environment.baseUrl + "/decrease-stock/" + product.id, {}, this.getAuthToken());
  }

  increaseStock(product: Product) {
    return this.httpClient.patch<Product[]>(environment.baseUrl+ "/increase-stock/" + product.id, {}, this.getAuthToken());
  }
  
}
