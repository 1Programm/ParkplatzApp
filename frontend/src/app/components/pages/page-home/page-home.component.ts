import { Component, OnInit } from '@angular/core';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { BuchungService } from 'src/app/services/buchung.service';

@Component({
  selector: 'app-home',
  templateUrl: './page-home.component.html',
  styleUrls: ['./page-home.component.scss']
})
export class PageHomeComponent implements OnInit {
parkplaetze: Parkplatz[];
  constructor(private buchungService: BuchungService) { }

  ngOnInit(): void {
  //  this.buchungService.getParkplaetzeOfParkflaeche(1).subscribe( data => {
  //   this.parkplaetze = data;
  //   console.log("data; ", data)
  //  });
  }

}
