import { Component, OnInit } from '@angular/core';
import { BuchungService } from 'src/app/services/buchung.service';
import { AccountService } from 'src/app/services/account.service';
import {ParkflaecheAuswahlDto } from '../../../facade/dto/parkflaeche-auswahl.dto';
import { BuchungDto } from 'src/app/facade/dto/BuchungDto';
import { Kennzeichen } from 'src/app/facade/Kennzeichen';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-buchen-page',
  templateUrl: './buchen-page.component.html',
  styleUrls: ['./buchen-page.component.scss']
})
export class BuchenPageComponent implements OnInit {

  parkanlagen : ParkflaecheAuswahlDto[];
  selectedParkanlage: ParkflaecheAuswahlDto;
  selectedDatum: string = new Date().toLocaleDateString();
  buchungen: any[] = [];
  kennzeichen: string[] = [];
  public testAttribs = []

  constructor(private buchungService: BuchungService, private accountService: AccountService) {}

  ngOnInit(): void {
    this.getParkAnlagen();

    this.accountService.getMitarbeiterIDAsObservable().subscribe(mitarbeiterID => {  
      // Abrufen der Buchungen für den Mitarbeiter
      this.buchungService.getBuchungenForMitarbeiter().subscribe((data: BuchungDto[]) => {
        this.buchungen.length = 0;
        for(let item of data) {
          this.buchungen.push({
            buchungID: item.buchungID,
            datum: this.dateFormat(item.datum),
            tagespreis: item.tagespreis,
            parkplatzKennung: item.parkplatzKennung,
            kennzeichen: item.kennzeichen.kennzeichen
          });
        }
      });

      // Abrufen der Kennzeichen für den Mitarbeiter
      this.buchungService.getKennzeichenForMitarbeiter().subscribe((data: Kennzeichen[]) => {
        this.kennzeichen.length = 0;
        
        for(let item of data) {
          console.log(item);
          this.kennzeichen.push(item.kennzeichen);
        }
      });
      this.testAttribs = [{name: 'datum'},
      {name: 'parkplatzKennung', label: "Parkfläche"},
      {name: 'kennzeichen', typ: this.kennzeichen}];
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

  public onDelete(pos: number){
    console.log("Deleted Index: ", pos);
  }

  dateFormat(datum: Date): string {
    // Formatieren des Datums im gewünschten Format
    return formatDate(datum, 'dd/MM/YYYY', "de-DE");
  }
}
