import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink, RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink,RouterModule,FormsModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  private router = inject(Router);
  searchTerm: string = '';
  selectedCategory: number = 0;

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
  suggestions: any[] = [];


  onSearch() {
      if (this.selectedCategory !== 0) {

          const category = this.categories.find(
              category => category.id === this.selectedCategory
          );

          this.router.navigate(
              [`search/${category.name}`],
              {
                  queryParams: {
                      name: this.searchTerm || null
                  }
              }
          );

      } else {

          this.router.navigate(
              ['search'],
              {
                  queryParams: {
                      name: this.searchTerm || null
                  }
              }
          );

      }
  }
  products = [
    {
      id: 1,
      name: 'Hemijska olovka',
      category: 'Reklamni materijal'
    },
    {
      id: 2,
      name: 'Polo majica',
      category: 'Tekstil'
    },
    {
      id: 3,
      name: 'Šolja',
      category: 'Reklamni materijal'
    },
    {
      id: 4,
      name: 'Vizit karta',
      category: 'Papirni proizvodi'
    },
    {
      id: 5,
      name: 'Majica',
      category: 'Tekstil'
    }
  ];


  onSearchInput() {

    const term = this.searchTerm.trim().toLowerCase();
    if (!term) {
      this.suggestions = [];
      return;
    }

    this.suggestions = this.products
      .filter(product =>
        product.name.toLowerCase().includes(term)
      )
      .slice(0, 5);
  }

  selectProduct(product: any) {

    this.searchTerm = product.name;

    this.suggestions = [];

    this.onSearch();
  }
}
