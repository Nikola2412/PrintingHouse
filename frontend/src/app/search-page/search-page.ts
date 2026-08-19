import { Component, inject, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductsService } from '../services/products-service';
import { Product } from '../models/Product';
import { combineLatest } from 'rxjs';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-search-page',
  imports: [FormsModule],
  templateUrl: './search-page.html',
  styleUrl: './search-page.css',
})
export class SearchPage implements OnInit{
  private router = inject(Router);
  private activatedRoute = inject(ActivatedRoute);
  private productService = inject(ProductsService);
  gridView: boolean = false;
  category: string = '';
  searchTerm: string = '';
  searchResults: Product[] = [];
  ngOnInit(): void {
    combineLatest([
      this.activatedRoute.paramMap,
      this.activatedRoute.queryParamMap
    ]).subscribe(([params, queryParams]) => {
      this.category = params.get('category') || '';
      this.searchTerm = queryParams.get('search') || '';

      this.productService
        .findProducts(this.category, this.searchTerm)
        .subscribe(data => {
          this.searchResults = data;
          console.log(this.searchResults);
        });
    });
  }

  Ditails(product: Product){    
    this.router.navigate([`product/${product.id}`]);
  }
}
