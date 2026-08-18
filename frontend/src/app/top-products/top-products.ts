import { Component, inject, OnInit } from '@angular/core';
import { Product } from '../models/Product';
import { ProductsService } from '../services/products-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-top-products',
  imports: [FormsModule],
  templateUrl: './top-products.html',
  styleUrl: './top-products.css',
})
export class TopProducts implements OnInit {
  private productsService = inject(ProductsService);
  products: Product[] = [];
  productCnt: number = 0;
  ngOnInit(): void {
    this.productsService.getTopProducts().subscribe(data=>{
      this.products = data;
      console.log(this.products)
    })
    this.productsService.getProductCnt().subscribe(data=>{
      this.productCnt = parseInt(data.msg);
    })

  }
}
