import { Routes } from '@angular/router';
import { Frontpage } from './frontpage/frontpage';
import { SearchPage } from './search-page/search-page';

export const routes: Routes = [
    {path:"",component:Frontpage},
    {path:"search",component:SearchPage},
    {path:"search/:category",component:SearchPage}
];
