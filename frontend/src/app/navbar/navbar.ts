import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { Product } from '../models/Product';
import { ProductsService } from '../services/products-service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink,RouterModule,FormsModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  private router = inject(Router);
  private productService = inject(ProductsService);
  searchTerm: string = '';
  selectedCategory: number = 0;
  suggestions: Product[] = [];

  categories: any[] = [
        {
            id: 1,
            name: "Stampa malih formata"
        },
        {
            id: 2,
            name: "Stampa velikih formata"
        },
        {
            id: 3,
            name: "Kreativne stampe"
        }
    ];


    onSearch() {
        this.suggestions = [];
        if (this.selectedCategory !== 0) {
            const category = this.categories.find(
            category => category.id === this.selectedCategory
        );

        this.router.navigate(
            [`search/${category.name}`],
            {
                queryParams: {
                    search: this.searchTerm || null
                }
            }
        );
      } else {
        this.router.navigate(['search'],
            {
                queryParams: {
                    search: this.searchTerm || null
                }
            }
          );
      }
    }
    onSearchInput() {
        const term = this.searchTerm.trim();
        if (!term) {
        this.suggestions = [];
        return;
        }
        const category = this.categories.find(
            category => category.id === this.selectedCategory
        );
        this.productService.findProducts(category?.name || '', this.searchTerm).subscribe(data => {
        this.suggestions = data;
        })
    }

    selectProduct(product: any) {
        this.searchTerm = product.name;
        this.suggestions = [];
        this.onSearch();
    }
}
