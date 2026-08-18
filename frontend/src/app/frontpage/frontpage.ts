import { Component } from '@angular/core';
import { TopProducts } from "../top-products/top-products";

@Component({
  selector: 'app-frontpage',
  imports: [TopProducts],
  templateUrl: './frontpage.html',
  styleUrl: './frontpage.css',
})
export class Frontpage {

}
