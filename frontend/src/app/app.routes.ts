import { Routes } from '@angular/router';
import { Frontpage } from './frontpage/frontpage';
import { SearchPage } from './search-page/search-page';
import { TestApp } from './test-app/test-app';
import { ProductDitails } from './product-ditails/product-ditails';

export const routes: Routes = [
    {path:"",component:TestApp},
    {path:"home",component:Frontpage},
    {path:"search",component:SearchPage},
    {path:"search/:category",component:SearchPage},
    {path:"product/:id",component:ProductDitails}
];
