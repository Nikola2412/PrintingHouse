import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductDitails } from './product-ditails';

describe('ProductDitails', () => {
  let component: ProductDitails;
  let fixture: ComponentFixture<ProductDitails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductDitails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductDitails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
