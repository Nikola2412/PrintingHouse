import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Product } from '../models/Product';
import { Message } from '../models/Message';

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

  findProducts(param: string) {
    return this.http.get<Product[]>(`${this.path}/find?param=${param}`);
  }
}
