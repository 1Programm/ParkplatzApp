import { Component } from '@angular/core';

@Component({
  selector: 'app-buchen-page',
  templateUrl: './buchen-page.component.html',
  styleUrls: ['./buchen-page.component.scss']
})
export class BuchenPageComponent {
  options: { label: string }[] = [
    { label: 'PK-123' },
    { label: 'PK-234' },
    { label: 'PK-345' },
    { label: 'PK-456' },
    { label: 'PK-567' },
    { label: 'PK-678' },
];
selected: { label: string } = this.options[1];
}
