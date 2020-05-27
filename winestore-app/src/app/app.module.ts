import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';
import {HomeComponent} from './home/home.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {CardComponent} from './card/card.component';
import {FeedbackComponent} from './feedback/feedback.component';
import {ShoppingCartComponent} from './shopping-cart/shopping-cart.component';
import {ShoppingCartCardComponent} from './shopping-cart/shopping-cart-card/shopping-cart-card.component';
import {WineTypeComponent} from './wine-type/wine-type.component';
import {CheckoutComponent} from './checkout/checkout.component';
import {ShopComponent} from './shop/shop.component';
import {ProductCardComponent} from './shop/product-card/product-card.component';
import {NgxPaginationModule} from 'ngx-pagination';
import {SigninComponent} from './auth/signin/signin.component';
import {MainComponent} from './main/main.component';
import {SignupComponent} from './auth/signup/signup.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    NotFoundComponent,
    CardComponent,
    FeedbackComponent,
    ShoppingCartComponent,
    ShoppingCartCardComponent,
    WineTypeComponent,
    CheckoutComponent,
    ShopComponent,
    ProductCardComponent,
    SigninComponent,
    MainComponent,
    SignupComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgxPaginationModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
