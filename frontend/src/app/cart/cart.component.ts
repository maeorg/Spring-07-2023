import { Component } from '@angular/core';
import { CartProduct } from '../models/cart-product.model';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent {
  cartProducts: CartProduct[] = [];
  omnivaPMs: any[] = [];
  smartPostPMs: any[] = [];
  sumOfCart = 0;

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    const cartItemsSS = sessionStorage.getItem("cartItems");
    if (cartItemsSS) {
      this.cartProducts = JSON.parse(cartItemsSS); 
    }
    this.calculateSumOfCart();
    // this.cartService.getParcelMachines().subscribe(res => {
    //   this.parcelmachines = res;
    // })
    this.onChangeParcelMachine("ee");
  }

  // nupuvajutus kas ee, lv ,lt
  onChangeParcelMachine(country: string) {
    this.cartService.getParcelMachines().subscribe(res => {
      this.omnivaPMs = res.omnivaPMs;
      this.smartPostPMs = res.smartPostPMs;
    })
  }

  onDecreaseQuantity(cartProduct: CartProduct) {
    cartProduct.quantity--;
    if (cartProduct.quantity <= 0) {
      this.onRemoveProduct(cartProduct);
    }
    sessionStorage.setItem("cartItems", JSON.stringify(this.cartProducts));
    this.calculateSumOfCart();
  }

  onIncreaseQuantity(cartProduct: CartProduct) {
    cartProduct.quantity++;
    sessionStorage.setItem("cartItems", JSON.stringify(this.cartProducts));
    this.calculateSumOfCart();
  }

  onRemoveProduct(cartProduct: CartProduct) {
    const index = this.cartProducts.findIndex(element => element.product.id === cartProduct.product.id);
    if (index >= 0) {
      this.cartProducts.splice(index,1);
      sessionStorage.setItem("cartItems", JSON.stringify(this.cartProducts));
    }
    this.calculateSumOfCart();
  
  }

  onEmptyCart() {
    this.cartProducts = [];
    sessionStorage.setItem("cartItems", JSON.stringify(this.cartProducts));
    this.calculateSumOfCart();
  }

  calculateSumOfCart() {
    this.sumOfCart = 0;
    this.cartProducts.forEach(element => this.sumOfCart += element.product.price * element.quantity);
  }

  onPay() {
    this.cartService.getPaymentLink(this.cartProducts).subscribe(res => {
      window.location.href = res;
    });
  }
}