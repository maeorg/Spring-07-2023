import { Component } from '@angular/core';
import { FormControl, FormControlName, FormGroup, NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Category } from 'src/app/models/category.models';
import { Product } from 'src/app/models/product.model';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent {
  categories: Category[] = [];
  product!: Product;
  editProductForm!: FormGroup;

  constructor(private productService: ProductService, 
    private categoryService: CategoryService,
    private route: ActivatedRoute) {}

  ngOnInit() { 
    const productId = this.route.snapshot.paramMap.get("id");
    if (productId == null) {
      return;
    }
    this.productService.getProduct(Number(productId)).subscribe(data => {
        this.product = data
        this.editProductForm = new FormGroup({
          name: new FormControl(this.product.name),
          price: new FormControl(this.product.price),
          image: new FormControl(this.product.image),
          active: new FormControl(this.product.active),
          description: new FormControl(this.product.description),
          stock: new FormControl(this.product.stock),
          category: new FormControl(this.product.category.id),
        });
      });

    this.categoryService.getCategories().subscribe(data => this.categories = data);
  }

  handleSubmit() {
    const formValue = this.editProductForm.value;

    const newProduct = new Product(
      formValue.name,
      formValue.price,
      formValue.image,
      formValue.active,
      formValue.description,
      formValue.stock,
      new Category(formValue.category),
      this.product.id
    );

    this.productService.editProduct(newProduct).subscribe();
  }
}
