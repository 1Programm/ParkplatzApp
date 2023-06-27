import { Component, OnInit } from '@angular/core';
import { BuchungService } from 'src/app/services/buchung.service';
import { AccountService } from 'src/app/services/account.service';
import {ParkflaecheAuswahlDto } from '../../../facade/dto/parkflaeche-auswahl.dto';
import { BuchungDto } from 'src/app/facade/dto/BuchungDto';
import { Parkflaeche } from 'src/app/facade/Parkflaeche';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { ParkplatzMitStatusDto } from 'src/app/facade/dto/ParkplatzMitStatusDto';
import { AccountService } from 'src/app/services/account.service';
import { Kennzeichen } from 'src/app/facade/Kennzeichen';
import { formatDate } from '@angular/common';
import { AttribInfo } from '../../core/edit-list/edit-list.component';

@Component({
  selector: 'app-buchen-page',
  templateUrl: './buchen-page.component.html',
  styleUrls: ['./buchen-page.component.scss']
})
export class BuchenPageComponent implements OnInit {
  public parkplaetze: ParkplatzMitStatusDto[];
  public parkanlagen : ParkflaecheAuswahlDto[] ;
  selectedParkanlage: ParkflaecheAuswahlDto = {parkflaecheID: 1};
  selectedDatum: string = new Date().toISOString();
  isAdmin: boolean = this.accountService.isAdmin;
  kennzeichen: string[] = [];
  buchungen: any[] = [];
  public mitarbeiterID: number = 0;
  public buchungAttribs: AttribInfo[];

  constructor(private buchungService: BuchungService, private accountService: AccountService) {}

  ngOnInit(): void {
    this.mitarbeiterID = this.accountService.getMitarbeiterID()
    this.getParkAnlagen();

      // Abrufen der Buchungen für den Mitarbeiter
      this.buchungService.getBuchungenForMitarbeiter(this.mitarbeiterID).subscribe((data: BuchungDto[]) => {
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
      this.buchungService.getKennzeichenForMitarbeiter(this.mitarbeiterID).subscribe((data: Kennzeichen[]) => {
        this.kennzeichen.length = 0;

        for(let item of data) {
          this.kennzeichen.push(item.kennzeichen);
        }
      });
      this.buchungAttribs =  [
        {name: 'datum'},
        {name: 'parkplatzKennung', label: "Parkfläche"},
        {name: 'kennzeichen', typ: this.kennzeichen}
        ];
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

  public updateBuchungen() {
    let buchung: BuchungDto = new BuchungDto();

    // let kennzeichen: Kennzeichen = {
    //   kennzeichenID: 1,
    //   kennzeichen: "DO-JB1999"
    // };

    // buchung.kennzeichen = kennzeichen;
    // buchung.parkplatzKennung = this.buchungen[0].parkplatzKennung;
    // buchung.tagespreis = this.buchungen[0].tagespreis;
    // buchung.datum = this.parseDateString(this.buchungen[0].datum);

    console.log("TEST UpdateBuchung-Methode", buchung);
    buchung.parkplatzKennung = this.buchungen[0].parkplatzKennung;
    this.buchungService.updateBuchungen(buchung).subscribe();
  }

  private dateFormat(datum: Date): string {
    // Formatieren des Datums im gewünschten Format
    return formatDate(datum, 'dd/MM/YYYY', "de-DE");
  }

  addSpotToBasket(buchung: BuchungDto) {
    console.log("bla", )
  }
}
