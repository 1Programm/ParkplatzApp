import { Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild } from '@angular/core';
import { ILuxDialogPresetConfig, LuxDialogService } from '@ihk-gfi/lux-components';
import { BuchungDto } from 'src/app/facade/dto/BuchungDto';
import { ParkflaecheAuswahlDto } from 'src/app/facade/dto/parkflaeche-auswahl.dto';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { ParkplatzMitStatusDto } from 'src/app/facade/dto/ParkplatzMitStatusDto';
import { AccountService } from 'src/app/services/account.service';
import { BuchungService } from 'src/app/services/buchung.service';
import { MarkerDialogComponent } from '../../dialogs/marker-dialog/marker-dialog.component';
import { AdminService } from 'src/app/services/admin.service';


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {
  public showNewMarker = false;
  public newMarkerX = 0;
  public newMarkerY = 0;
  public isAdmin = this.accountService.isAdmin;

  @Input() marker: ParkplatzMitStatusDto[];
  @Input() image: any;
  @Output() onParkplatzToBasket = new EventEmitter<Parkplatz>();
  @Output() onMarkerDeleted = new EventEmitter<number>();
  @Output() onMarkerChanged = new EventEmitter<Parkplatz>();

  dialogConfig: ILuxDialogPresetConfig = {
    disableClose: true,
    width: 'auto',
    height: 'auto',
    panelClass: []
  };


  constructor(
    private dialogService: LuxDialogService,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {}

 
  //wird ausgeführt, wenn der User auf einen Marker klickt
  handleUserMarkerClick(event: MouseEvent, spot: ParkplatzMitStatusDto): void {
    event.stopPropagation();
    const dialogRef = this.dialogService.openComponent(MarkerDialogComponent, this.dialogConfig, spot);

    dialogRef.dialogClosed.subscribe((result: Parkplatz) => {
      if (result != null) {
        this.onParkplatzToBasket.emit(result);
        spot.status = "BELEGT"
      }
      this.showNewMarker = false;
    });
  }

  //wird ausgeführt, wenn der Admin auf einen bestehenden Marker klickt
  handleAdminMarkerClick(event: MouseEvent, spot: ParkplatzMitStatusDto): void {
    event.stopPropagation();
    const dialogRef = this.dialogService.openComponent(MarkerDialogComponent, this.dialogConfig, spot);
    let newSpot: Parkplatz;

    dialogRef.dialogClosed.subscribe((result) => {
      if (result != null) {
        if (result.nummer == undefined) {
          this.onMarkerDeleted.emit(result);
        } else {
         this.onMarkerChanged.emit(result);
        }
      }
      this.showNewMarker = false;
    });
  }

  //wird ausgeführt, wenn der Admin auf die Karte klickt; fügt einen temporären Marker hinzu
  handleMapClick(event: MouseEvent): void {
    this.showNewMarker = true;
    const container = event.currentTarget as HTMLElement;
    const boundingRect = container.getBoundingClientRect();
    this.newMarkerX = event.clientX - boundingRect.left - 10;
    this.newMarkerY = event.clientY - boundingRect.top - 10;
  }

  //wird ausgeführt, wenn der Admin auf den temporären neuen Marker klickt (noch nicht ausgefüllt)
  handleNewMarkerClicked(event): void {
    event.stopPropagation();
    const dialogRef = this.dialogService.openComponent(MarkerDialogComponent, this.dialogConfig);
    let newSpot: Parkplatz;

    dialogRef.dialogClosed.subscribe((result: Parkplatz) => {
      if(result){
        newSpot = result;
        newSpot.xkoordinate = this.newMarkerX;
        newSpot.ykoordinate = this.newMarkerY;
        this.onMarkerChanged.emit(newSpot);
      }
      
      this.showNewMarker = false;
    });
  }

  getPositionStyle(spot: ParkplatzMitStatusDto): any {
    return {
      left: `${spot.parkplatz.xkoordinate}px`,
      top: `${spot.parkplatz.ykoordinate}px`
    };
  }

  getMarkerColor(marker: any): string {
    if (marker.status === 'FREI') {
      return 'green';
    }
    return 'red';
  }

  
  
}
