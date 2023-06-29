import { Component, OnInit } from '@angular/core';
import { BuchungService } from 'src/app/services/buchung.service';
import { AccountService } from 'src/app/services/account.service';
import {ParkflaecheAuswahlDto } from '../../../facade/dto/parkflaeche-auswahl.dto';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { Kennzeichen } from 'src/app/facade/Kennzeichen';
import { BuchungAbschlussDto } from 'src/app/facade/dto/BuchungAbschluss.dto';
import { LuxSnackbarService } from '@ihk-gfi/lux-components';
import { DateUtils } from 'src/app/utils/date.utils';

@Component({
  selector: 'app-buchen-page',
  templateUrl: './buchen-page.component.html',
  styleUrls: ['./buchen-page.component.scss']
})
export class BuchenPageComponent implements OnInit {

  public mitarbeiterID: number = this.accountService.getMitarbeiterID();
  public isAdmin: boolean = this.accountService.isAdmin;

  public parkflaechen : ParkflaecheAuswahlDto[];
  public kennzeichenList: Kennzeichen[];

  public minDate: string = DateUtils.getTodayAsString();
  public maxDate: string = DateUtils.getFuture_2WeeksAsString();

  public selectedParkflaeche: ParkflaecheAuswahlDto;
  public selectedDatum: Date = DateUtils.getToday();
  public abschlussBuchungen: BuchungAbschlussDto[] = [];

  constructor(private buchungService: BuchungService, private accountService: AccountService, private snackbarService: LuxSnackbarService) {}

  ngOnInit(): void {
    // Abrufen der Parkfl
    this.buchungService.getParkflaechen().subscribe(parkflaechen => {
      this.parkflaechen = parkflaechen;
      this.selectedParkflaeche = parkflaechen[0];
    });

    // Abrufen der Kennzeichen für den Mitarbeiter
    this.buchungService.getKennzeichenForMitarbeiter(this.mitarbeiterID).subscribe((data: Kennzeichen[]) => {
      this.kennzeichenList = data;
    });

  }

  private setupBuchungForUI(buchung: BuchungAbschlussDto){
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

  //Change Methode gibt ein string zurück, jedoch wollen wir ein Date-Objekt
  public onSelectedDatumChange(date){
    this.selectedDatum = new Date(date);
  }

  public addSpotToBasket(parkplatz: Parkplatz) {    
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
