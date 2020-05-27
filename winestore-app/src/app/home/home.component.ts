import {Component, OnInit} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Product} from '../model/product.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  products$: Observable<Product[]>;

  constructor() {
  }

  ngOnInit(): void {
    const products: Product[] = [
      {
        code: 'W0001',
        name: 'Shing Wang',
        description: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
        price: 3432.00,
        region: 'Russia',
        year: 2006,
        image: null,
      },
      {
        code: 'W0002',
        name: 'Koroko Tok',
        description: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
        price: 1999.00,
        region: 'China',
        year: 2019,
        image: null,
      },
      {
        code: 'W0003',
        name: 'Tokis Balaytis',
        description: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
        price: 1322.00,
        region: 'Philippines',
        year: 2020,
        image: null
      }
    ];
    this.products$ = of(products);
  }

}
