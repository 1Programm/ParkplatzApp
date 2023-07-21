import { Component, OnInit } from '@angular/core';
import { BuchungService } from 'src/app/services/buchung.service';
import {ParkflaecheAuswahlDto } from '../../../facade/dto/parkflaeche-auswahl.dto';
import { BuchungAbschlussDto } from 'src/app/facade/dto/BuchungAbschluss.dto';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { Kennzeichen } from 'src/app/facade/Kennzeichen';
import { DateUtils } from 'src/app/utils/date.utils';
import { AccountService } from 'src/app/services/account.service';
import { LuxDialogService, LuxSnackbarService } from '@ihk-gfi/lux-components';
import { AdminService } from 'src/app/services/admin.service';
import { ImageUtils } from 'src/app/utils/image.utils';
import { ParkplatzMitStatusDto } from '../../../facade/dto/ParkplatzMitStatusDto';
import { KennzeichenHinzufuegenDialogComponent } from '../../dialogs/kennzeichen-hinzufuegen-dialog/kennzeichen-hinzufuegen-dialog.component';
import { ProfilServiceService } from 'src/app/services/profil-service.service';
import { AktivitaetsEnum } from 'src/app/utils/aktivitaetEnum.utils';
import { Parkhaus } from 'src/app/facade/Parkhaus';
import { ParkhausAdressDto } from 'src/app/facade/dto/ParkhausAdress.dto';

@Component({
  selector: 'app-buchen-page',
  templateUrl: './buchen-page.component.html',
  styleUrls: ['./buchen-page.component.scss']
})
export class BuchenPageComponent implements OnInit {

  public mitarbeiterID: number = this.accountService.getMitarbeiterID();
  public isAdmin: boolean = this.accountService.isAdmin;
  public parkflaechen : ParkflaecheAuswahlDto[];
  
  private _selectedParkflaeche: ParkflaecheAuswahlDto;
  public set selectedParkflaeche(parkflaeche){
    this._selectedParkflaeche = parkflaeche;
    this.loadParkhausAddress();
  }
  public get selectedParkflaeche() {
    return this._selectedParkflaeche;
  }

  public parkhausAddress: ParkhausAdressDto;
  public kennzeichenList: Kennzeichen[];

  public selectedDatum: Date = DateUtils.getToday();
  public minDate: string = DateUtils.getTodayAsString();
  public maxDate: string = DateUtils.getFuture_2WeeksAsString();

  public abschlussBuchungen: BuchungAbschlussDto[] = [];

  image: any;
  public marker: ParkplatzMitStatusDto[];
  dialogConfig: any;

  constructor(private buchungService: BuchungService, private accountService: AccountService, private snackbarService: LuxSnackbarService, private adminService: AdminService, private profilService: ProfilServiceService, private dialogService: LuxDialogService) {}

  ngOnInit(): void {
    // Abrufen der Parkflächen
    this.loadParkflaeche();
    // Abrufen der Kennzeichen für den Mitarbeiter
    this.buchungService.getKennzeichenForMitarbeiter(this.mitarbeiterID).subscribe((data: Kennzeichen[]) => {
      this.kennzeichenList = data;
      this.loadFromSessionStorage();
    });
  }

  public loadParkflaeche(){
    this.buchungService.getParkflaechen().subscribe(parkflaechen => {
      this.parkflaechen = [];
      for(let parkflaeche of parkflaechen){
        if(parkflaeche.parkflaecheBezeichnung != null) {
          this.parkflaechen.push(parkflaeche);
        }
      }
      this.selectedParkflaeche = parkflaechen[0];
      this.loadMarker();

    });
  }

  private loadParkhausAddress(){
    this.buchungService.getParkhausAddress(this.selectedParkflaeche.parkhausID).subscribe(data => {
      this.parkhausAddress = data;
      this.parkhausAddress["_bezeichnung"] = data.strasse + " " + data.hausnummer + " " + data.ort + " " + data.plz;
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
    this.loadMarker();
  }

  public onSelectedParkflaecheChange(){
    this.loadMarker();
  }

  private loadFromSessionStorage(){
      let _buchungLength = sessionStorage.getItem("buchung_length");
      if(_buchungLength){
        this.abschlussBuchungen = [];

        let buchungLength = +_buchungLength;
        for(let i=0;i<buchungLength;i++){
          let _buchungDto = sessionStorage.getItem("buchung" + i);
          let buchungDto: BuchungAbschlussDto = JSON.parse(_buchungDto);
          buchungDto.datum = new Date(buchungDto.datum);

          this.setupBuchungForUI(buchungDto);
          this.abschlussBuchungen.push(buchungDto);
        }
      }
  }

  private updateSessionStorage(){
    let _oldLength = sessionStorage.getItem("buchung_length");
    if(_oldLength){
      let oldLength = +_oldLength;
      for(let i=0;i<oldLength;i++){
        sessionStorage.removeItem("buchung" + i);
      }
    }

    sessionStorage.setItem("buchung_length", "" + this.abschlussBuchungen.length);
    for(let i=0;i<this.abschlussBuchungen.length;i++){
      let buchung = this.abschlussBuchungen[i];
      sessionStorage.setItem("buchung" + i, JSON.stringify(buchung));
    }
  }

  public addParkplatzToBasket(parkplatz: Parkplatz) {
    if(this.kennzeichenList.length === 0) {
      const dialogRef = this.dialogService.openComponent(KennzeichenHinzufuegenDialogComponent, this.dialogConfig, null);
      dialogRef.dialogClosed.subscribe((result: string) => {
        if (result != null) {
          this.profilService.createKennzeichenForMitarbeiter(this.mitarbeiterID, result).subscribe((data) => {
            this.kennzeichenList = data.kennzeichenList;
            this.addParkplatzToBasket(parkplatz);
          });
        }
      });
    } else {
      let newBuchung: BuchungAbschlussDto = {
        parkplatzKennung: this.selectedParkflaeche.parkhausBezeichnung + "-" + this.selectedParkflaeche.parkflaecheBezeichnung + "-" + parkplatz.nummer,
        datum: this.selectedDatum,
        kennzeichen: this.kennzeichenList[0],
        parkplatz: parkplatz,
        mitarbeiterID: this.mitarbeiterID
      }
  

      this.setupBuchungForUI(newBuchung);
      this.abschlussBuchungen.push(newBuchung);
      this.updateSessionStorage();
    }
  }

  public cancelBuchung(index: number){
    this.abschlussBuchungen.splice(index, 1);
    this.updateSessionStorage();
    this.marker[index].status = "FREI";
  }

  public confirmBuchung(){
    this.buchungService.saveBuchungen(this.abschlussBuchungen).subscribe(() => {
      this.abschlussBuchungen = [];
      this.updateSessionStorage();
      this.snackbarService.openText('Buchung erfolgreich!', 3000);
      this.loadMarker();
      
    });
  }


  //alle sachen für die Map und die Marker
  private loadMarker(): void {
    if(this.selectedDatum === undefined) return;
    if(this.selectedParkflaeche == undefined) return;
    this.adminService.getImageForParkflaeche(this.selectedParkflaeche.parkflaecheID).subscribe(image => {
      ImageUtils.readAsDataUrl(image).subscribe(image => {
        this.image = image;
      });
    })
    if (this.isAdmin) {
      this.buchungService.getParkplaetzeOfParkflaeche(this.selectedParkflaeche.parkflaecheID).subscribe((data) => {
        this.marker = [];
        for(let parkplatz of data){
          this.marker.push(ParkplatzMitStatusDto.toParkplatzMitStatusDto(parkplatz));
        }
      });
    } else {
      this.buchungService.getParkplaetzeOfParkflaecheAndDate(this.selectedParkflaeche.parkflaecheID, this.selectedDatum).subscribe((data) => {
        this.marker = data;
        for(let buchung of this.abschlussBuchungen){
          this.marker.forEach((marker) => {
            
            if(marker.parkplatz.parkplatzID === buchung.parkplatz.parkplatzID && buchung.datum.getTime() === this.selectedDatum.getTime()) {
              //console.log("marker: " + marker.parkplatz.parkplatzID + " buchung: " + buchung.parkplatz.parkplatzID)
              marker.status = "BELEGT";
            }
        });
      }
      });
    }


  }

  public onMarkerChanged(parkplatz: Parkplatz) {
    this.adminService.saveParkplatz(parkplatz, this.selectedParkflaeche.parkflaecheID).subscribe((parkplaetze) => {
      this.marker = []
      for(let parkplatz of parkplaetze){
        this.marker.push(ParkplatzMitStatusDto.toParkplatzMitStatusDto(parkplatz));
      }
  });
  }
  public onMarkerDeleted(parkplatz: number) {
    this.adminService.deleteParkplatz(parkplatz).subscribe((parkplatz) => {
      if (parkplatz != null) {
        this.loadMarker();
      }
    });
  }

  public isSelectedDatumToday(): boolean {
    let today = DateUtils.removeTimeFromDate(DateUtils.getToday());
    let selected = DateUtils.removeTimeFromDate(new Date(this.selectedDatum.getTime()));
    return today.getTime() === selected.getTime();
  }

}