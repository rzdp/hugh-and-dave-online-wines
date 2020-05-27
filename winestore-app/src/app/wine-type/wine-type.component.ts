import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-wine-type',
  templateUrl: './wine-type.component.html',
  styleUrls: ['./wine-type.component.scss']
})
export class WineTypeComponent implements OnInit {

  @Input() name: string;
  @Input() description: string;
  @Input() imageSrc: string;
  @Input() reverse: boolean;

  constructor() {
  }

  ngOnInit(): void {
  }

}
