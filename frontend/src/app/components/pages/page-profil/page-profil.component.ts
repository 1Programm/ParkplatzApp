import { Component, OnInit } from '@angular/core';
import { ProfildatenDto } from 'src/app/facade/dto/ProfildatenDto';

@Component({
  selector: 'app-page-profil',
  templateUrl: './page-profil.component.html',
  styleUrls: ['./page-profil.component.scss']
})
export class PageProfilComponent implements OnInit {
attr: any[] = [{name: "kennzeichen", label: "Kennzeichen"}];
test: any[];
index: number = 99;
  constructor() {
    this.test = [{kennzeichen: "bla"}, {kennzeichen: "bla"}];
   }

  ngOnInit(): void {
  }

  deleteKennzeichen(index: number): void {
    
    let test1: any[] = [];
    for(let i = 0; i < this.test.length; i++) {
      if(i != index)
      test1.push(this.test[i].kennzeichen);
    }
    this.test = test1;
    console.log("t3est ", this.test)
  }

}
