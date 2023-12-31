import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-maintain-products',
  templateUrl: './maintain-products.component.html',
  styleUrls: ['./maintain-products.component.scss']
})
export class MaintainProductsComponent {

  products: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit() { // reserveeritud funktsioon ComponentDidMount
    this.productService.getProducts()
    .subscribe((data: Product[]) => {
      this.products = data;
    });
  }
  
  deleteProduct(product: Product) {
    this.productService.deleteProduct(product).subscribe(res => 
      this.products = res
    );
  }

  decreaseStock(product: Product) {
    this.productService.decreaseStock(product).subscribe(res =>
      this.products = res);
  }

  increaseStock(product: Product) {
    this.productService.increaseStock(product).subscribe(res =>
      this.products = res);
  }

}