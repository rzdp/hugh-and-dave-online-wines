import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {ShoppingCartComponent} from './shopping-cart/shopping-cart.component';
import {CheckoutComponent} from './checkout/checkout.component';
import {ShopComponent} from './shop/shop.component';
import {SigninComponent} from './auth/signin/signin.component';
import {MainComponent} from './main/main.component';
import {SignupComponent} from './auth/signup/signup.component';


const routes: Routes = [
  {
    path: 'sign-in',
    component: SigninComponent
  },
  {
    path: 'sign-up',
    component: SignupComponent
  },
  {
    path: '', component: MainComponent,
    children: [
      {path: 'home', component: HomeComponent},
      {path: 'shop', component: ShopComponent},
      {path: 'shopping-cart', component: ShoppingCartComponent},
      {path: 'checkout', component: CheckoutComponent},
    ]
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled'})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
