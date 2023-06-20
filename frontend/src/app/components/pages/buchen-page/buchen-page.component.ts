import { Component, OnInit } from '@angular/core';
import { BuchungService } from 'src/app/services/buchung.service';
import { AccountService } from 'src/app/services/account.service';
import {ParkflaecheAuswahlDto } from '../../../facade/dto/parkflaeche-auswahl.dto';
import { BuchungDto } from 'src/app/facade/dto/BuchungDto';
import { Kennzeichen } from 'src/app/facade/Kennzeichen';

@Component({
  selector: 'app-buchen-page',
  templateUrl: './buchen-page.component.html',
  styleUrls: ['./buchen-page.component.scss']
})
export class BuchenPageComponent implements OnInit {

  parkanlagen : ParkflaecheAuswahlDto[];
  selectedParkanlage: ParkflaecheAuswahlDto;
  selectedDatum: string = new Date().toLocaleDateString();
  buchungen: BuchungDto[];
  kennzeichen: Kennzeichen[] = [];

  constructor(private buchungService: BuchungService, private accountService: AccountService) {}

  ngOnInit(): void {
    this.getParkAnlagen();

    this.accountService.getMitarbeiterIDAsObservable().subscribe(mitarbeiterID => {  
      // Abrufen der Buchungen für den Mitarbeiter
      this.buchungService.getBuchungenForMitarbeiter().subscribe((data: BuchungDto[]) => {
        this.buchungen = data;
        console.log(this.buchungen);
        
      });

      // Abrufen der Kennzeichen für den Mitarbeiter
      this.buchungService.getKennzeichenForMitarbeiter().subscribe((data: Kennzeichen[]) => {
        this.kennzeichen = data;
      });
    });
  }

  public getParkAnlagen() : ParkflaecheAuswahlDto[] {
    this.buchungService.getParkanlagen().subscribe(parkanlagen => {
      this.parkanlagen = parkanlagen;
      this.selectedParkanlage = parkanlagen[0];
    });
    return this.parkanlagen;
  }

  public buchenParkanlage() {
    console.log(this.selectedParkanlage);
  }

  public testAttribs = [
    //Validator inline
    {name: 'datum'},
    //Validator as a method
    {name: 'parkplatzKennung'},
    {name: 'kennzeichen', typ: this.kennzeichen}
  ]

  public onDelete(pos: number){
    console.log("Deleted Index: ", pos);
  }
}
