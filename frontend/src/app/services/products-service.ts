import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Product } from '../models/Product';
import { Message } from '../models/Message';
import { ProductInfo } from '../models/ProductInfo';

@Injectable({
  providedIn: 'root',
})
export class ProductsService { 
  private path = 'http://localhost:8080/products';
  private http = inject(HttpClient);

  getProductCnt() {
    return this.http.get<Message>(`${this.path}/getProductCnt`);
  }

  getTopProducts() {
    return this.http.get<Product[]>(`${this.path}/getTopProducts`);
  }

  productDitails(id:string) {
    return this.http.get<ProductInfo>(`${this.path}/productDitails?id=${id}`);
  }

  findProductById(id: string) {
    return this.http.get<Product>(`${this.path}/product?id=${id}`);
  }

  findProducts(category: string, param: string){
    if(category){
      return this.findProductsByCategory(category, param);
    }
    return this.findProductsByName(param);
  }

  findProductsByName(param: string) {
    return this.http.get<Product[]>(`${this.path}/find?param=${param}`);
  }

  findProductsByCategory(category: string, param: string) {
    return this.http.get<Product[]>(`${this.path}/findByCategory/${category}?param=${param}`);
  }
}
