import { Injectable } from '@angular/core';
import { Category } from '../models/category.models';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private httpClient: HttpClient) { }

  addCategory() {

  }

  deleteCategory() {

  }

  getCategories() {
    return this.httpClient.get<Category[]>("http://localhost:8080/categories");
  }

}
