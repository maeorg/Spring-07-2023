import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Product } from '../models/product.model';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent {

  products: Product[] = [];

  constructor(private httpClient: HttpClient) {} // Dependency Injection

  ngOnInit() { // reserveeritud funktsioon ComponentDidMount
    this.httpClient.get<Product[]>("http://localhost:8080/products").subscribe(res => this.products = res);
  }

  addToCart(product: Product) {
    // this.products = "LISASID OSTUKORVI";
  }
}
