import { Component, inject, OnInit } from '@angular/core';
import { Product } from '../models/Product';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductsService } from '../services/products-service';
import { ProductInfo } from '../models/ProductInfo';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-product-ditails',
  imports: [FormsModule],
  templateUrl: './product-ditails.html',
  styleUrl: './product-ditails.css',
})
export class ProductDitails implements OnInit{
  private activRoute = inject(ActivatedRoute);
  private productServices = inject(ProductsService);

  item: ProductInfo = new ProductInfo();

  selectedImageId: number = -1;

  ngOnInit(): void {
    const id = this.activRoute.snapshot.paramMap.get('id')!;

    this.productServices.productDitails(id).subscribe(data => {
      console.log('DATA:', data);
      console.log('IMAGES:', data.images);

      this.item = {
        ...data,
        images: data.images ?? []
      };

      const savedImageId = this.getSelectedImageFromCookie(this.item.id);
      const imageId = savedImageId !== null ? Number(savedImageId) : null;

      if (imageId !== null && this.item.images.includes(imageId)) {
        this.selectedImageId = imageId;
      } else {
        this.selectedImageId =
          this.item.images.length > 0
            ? this.item.images[0]
            : -1;
      }
      console.log(this.selectedImageId);
      
    });
  }

  getImageUrl(imageId: number | null): string {
      if (imageId === null) {
          return '';
      }
      return `http://localhost:8080/products/images/${imageId}`;
  }

  get thumbnails(): number[] {
    return this.item.images
      .filter(id => id !== this.selectedImageId).slice(0,3);
  }

  selectImage(imageId: number | null): void {
    if(imageId === null) return;
    console.log(imageId);
    
    this.selectedImageId = imageId;

    this.saveSelectedImageToCookie(
      this.item.id,
      imageId
    );
  }

  private getCookieName(productId: number): string {
    return `product_${productId}_selected_image`;
  }
  private saveSelectedImageToCookie(
    productId: number,
    imageId: number
  ): void {

    const cookieName = this.getCookieName(productId);

    const maxAge = 60 * 60 * 24 * 30;

    document.cookie =
      `${cookieName}=${imageId}; path=/; max-age=${maxAge}`;
  }

  private getSelectedImageFromCookie(productId: number): number | null {
    const cookieName = this.getCookieName(productId);
    const cookies = document.cookie.split(';');
    for (const cookie of cookies) {
      const [name, value] = cookie.trim().split('=');
      if (name === cookieName) {
        const imageId = Number(value);
        return isNaN(imageId) ? null : imageId;
      }
    }
    return null;
  }
}
