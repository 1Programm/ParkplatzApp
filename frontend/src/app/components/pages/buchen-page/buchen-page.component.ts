import { Component, OnInit } from '@angular/core';
import { BuchungService } from 'src/app/services/buchung.service';
import {ParkflaecheAuswahlDto } from '../../../facade/dto/parkflaeche-auswahl.dto';
import { BuchungDto } from 'src/app/facade/dto/BuchungDto';
import { BuchungAbschlussDto } from 'src/app/facade/dto/BuchungAbschluss.dto';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { Kennzeichen } from 'src/app/facade/Kennzeichen';
import { DateUtils } from 'src/app/utils/date.utils';
import { AccountService } from 'src/app/services/account.service';
import { LuxSnackbarService } from '@ihk-gfi/lux-components';
import { AdminService } from 'src/app/services/admin.service';
import { ImageUtils } from 'src/app/utils/image.utils';
import { ParkplatzMitStatusDto } from '../../../facade/dto/ParkplatzMitStatusDto';

@Component({
  selector: 'app-buchen-page',
  templateUrl: './buchen-page.component.html',
  styleUrls: ['./buchen-page.component.scss']
})
export class BuchenPageComponent implements OnInit {

  public mitarbeiterID: number = this.accountService.getMitarbeiterID();
  public isAdmin: boolean = this.accountService.isAdmin;
  public parkflaechen : ParkflaecheAuswahlDto[];
  public selectedParkflaeche: ParkflaecheAuswahlDto;
  public kennzeichenList: Kennzeichen[];

  
  public selectedDatum: Date | undefined = DateUtils.getToday();
  public minDate: string = DateUtils.getTodayAsString();
  public maxDate: string = DateUtils.getFuture_2WeeksAsString();

  public abschlussBuchungen: BuchungAbschlussDto[] = [];

  image: any;
  public marker: ParkplatzMitStatusDto[];

  constructor(private buchungService: BuchungService, private accountService: AccountService, private snackbarService: LuxSnackbarService, private adminService: AdminService) {}

  ngOnInit(): void {
    // Abrufen der Parkflächen
    this.loadParkflaeche();
    // Abrufen der Kennzeichen für den Mitarbeiter
    this.buchungService.getKennzeichenForMitarbeiter(this.mitarbeiterID).subscribe((data: Kennzeichen[]) => {
      this.kennzeichenList = data;
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

  public addParkplatzToBasket(parkplatz: Parkplatz) {
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
      this.loadMarker();
      
    });
  }


  //alle sachen für die Map und die Marker
  loadMarker(): void {
    if(this.selectedDatum === undefined) return;

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

  }


 

