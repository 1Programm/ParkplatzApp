import { Component, OnInit } from '@angular/core';
import { BuchungService } from 'src/app/services/buchung.service';
import { AccountService } from 'src/app/services/account.service';
import {ParkflaecheAuswahlDto } from '../../../facade/dto/parkflaeche-auswahl.dto';
import { BuchungDto } from 'src/app/facade/dto/BuchungDto';
import { Parkflaeche } from 'src/app/facade/Parkflaeche';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { ParkplatzMitStatusDto } from 'src/app/facade/dto/ParkplatzMitStatusDto';
import { Kennzeichen } from 'src/app/facade/Kennzeichen';
import { formatDate } from '@angular/common';
import { AttribInfo } from '../../core/edit-list/edit-list.component';
import { BuchungAbschlussDto } from 'src/app/facade/dto/BuchungAbschluss.dto';
import { LuxSnackbarService, today } from '@ihk-gfi/lux-components';

@Component({
  selector: 'app-buchen-page',
  templateUrl: './buchen-page.component.html',
  styleUrls: ['./buchen-page.component.scss']
})
export class BuchenPageComponent implements OnInit {
  public parkplaetze: ParkplatzMitStatusDto[];
  public parkflaechen : ParkflaecheAuswahlDto[];
  public selectedParkflaeche: ParkflaecheAuswahlDto;
  public minDate: string = new Date().toISOString();
  public maxDate: string = this.getMaxDate().toISOString();
  public selectedDatum: Date = new Date();//.toISOString();
  public isAdmin: boolean = this.accountService.isAdmin;
  public kennzeichenList: Kennzeichen[] = [];
  public abschlussBuchungen: BuchungAbschlussDto[] = [];
  public mitarbeiterID: number = 0;
  public buchungAttribs: AttribInfo[];

  constructor(private buchungService: BuchungService, private accountService: AccountService, private snackbarService: LuxSnackbarService) {}

  ngOnInit(): void {
    this.mitarbeiterID = this.accountService.getMitarbeiterID()
    
    // Abrufen der Parkfl
    this.buchungService.getParkflaechen().subscribe(parkflaechen => {
      this.parkflaechen = parkflaechen;
      this.selectedParkflaeche = parkflaechen[0];
    });

    // // Abrufen der Buchungen für den Mitarbeiter
    // this.buchungService.getBuchungenForMitarbeiter(this.mitarbeiterID).subscribe((data: BuchungDto[]) => {
    //   this.abschlussBuchungen = data;

    //   // this.setupUIData();
    // });

    // Abrufen der Kennzeichen für den Mitarbeiter
    this.buchungService.getKennzeichenForMitarbeiter(this.mitarbeiterID).subscribe((data: Kennzeichen[]) => {
      this.kennzeichenList = data;
      // this.kennzeichen.length = 0;

      // for(let item of data) {
      //   this.kennzeichen.push(item);
      // }
      // this.setupUIData();
    });

      // this.buchungAttribs =  [
      //   {name: 'datum'},
      //   {name: 'parkplatzKennung', label: "Parkfläche"},
      //   {name: 'kennzeichen', typ: this.kennzeichen}
      // ];
  }

  private getMaxDate(){
    let date: Date = new Date();

    //2 Wochen ... ?
    date.setDate(date.getDate() + 14);

    return date;
  }

  // private setupUIData(){
  //   if(this.buchungen === undefined || this.kennzeichen === undefined) return;

  //   for(let buchung of this.buchungen){
  //     this.setupBuchungForUI(buchung);
  //   }
  // }

  private setupBuchungForUI(buchung: BuchungAbschlussDto){
    //Datum is komisch formatiert ohne Umwandlung in ein Date objekt!

    buchung["_datum"] = new Date(buchung.datum);

    let kennzeichenObj = undefined;
    for(let kenn of this.kennzeichenList){
      if(kenn.kennzeichenID === buchung.kennzeichen.kennzeichenID){
        kennzeichenObj = kenn;
        break;
      }
    }

    //lux-select brauch genau das selbe objekt, um mit dem attribut luxSelected das kennzeichen anzeigen zu können
    buchung["_selectedKennzeichen"] = kennzeichenObj;
  }

  public onSelectedDatumChange(date){
    this.selectedDatum = new Date(date);
  }

  private buchenParkanlage() {
    console.log(this.selectedParkflaeche);
  }

  private onDelete(pos: number){
    console.log("Deleted Index: ", pos);
  }

  public addSpotToBasket(parkplatz: Parkplatz) {
    console.log(">>", parkplatz, this.selectedParkflaeche, this.selectedDatum);

    
    let newBuchung: BuchungAbschlussDto = {
      parkplatzKennung: this.selectedParkflaeche.parkhausBezeichnung + "-" + this.selectedParkflaeche.parkflaecheBezeichnung + "-" + parkplatz.nummer,
      datum: this.selectedDatum,
      kennzeichen: this.kennzeichenList[0],
      parkplatz: parkplatz,
      mitarbeiterID: this.mitarbeiterID
    }

    this.setupBuchungForUI(newBuchung);

    this.abschlussBuchungen.push(newBuchung);
  }

  public cancelBuchung(index: number){
    this.abschlussBuchungen.splice(index, 1);
  }

  public confirmBuchung(){
    this.buchungService.saveBuchungen(this.abschlussBuchungen).subscribe(() => {
      this.abschlussBuchungen = [];
      this.snackbarService.openText('Buchung erfolgreich!', 3000);
    });
  }
}
