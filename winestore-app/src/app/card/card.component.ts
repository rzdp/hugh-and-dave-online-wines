import {Component, Input, OnInit} from '@angular/core';
import {Product} from '../model/product.model';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss']
})
export class CardComponent implements OnInit {

  @Input() product: Product;

  constructor() {
  }

  ngOnInit(): void {
  }

}
